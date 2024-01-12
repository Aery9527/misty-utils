package org.misty.utils.task.executor;

import org.misty.utils.Tracked;
import org.misty.utils.Watcher;
import org.misty.utils.fi.RunnableEx;
import org.misty.utils.lock.WakeupLock;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskWaitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskParallelExecutor extends TaskBaseExecutor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicInteger currentTaskCount = new AtomicInteger(0);

    private final WakeupLock wakeupLock;

    private final long waitRestUnitMs;

    private final ExecutorService executorService;

    public TaskParallelExecutor(TaskParallelExecutorBuilder builder) {
        super(builder);
        this.wakeupLock = WakeupLock.ofReuseLock(super.getTracked());
        this.waitRestUnitMs = builder.getWaitRestUnitMs();
        this.executorService = builder.getExecutorService();
    }

    @Override
    protected void execute(RunnableEx executeAction) {
        this.currentTaskCount.incrementAndGet();

        this.executorService.execute(() -> {
            try {
                executeAction.execute();
            } finally {
                this.currentTaskCount.decrementAndGet();
                this.wakeupLock.wakeup();
            }
        });
    }

    @Override
    public TaskWaitResult waitFinish() {
        while (true) {
            TaskWaitResult waitResult = checkWaitResult();
            if (waitResult.stopWait()) {
                return waitResult;
            }

            this.wakeupLock.waiting(this.waitRestUnitMs, e -> {
                this.logger.error(getInterruptedMsg(), e);
                close();
            });
        }
    }

    @Override
    public TaskWaitResult waitFinish(Duration waitTime) {
        long waitMs = waitTime.toMillis();

        Watcher watcher = Watcher.create();
        while (!watcher.over(waitMs)) {
            long through = watcher.through();
            long timeout = Math.min(this.waitRestUnitMs, waitMs - through);

            if (timeout <= 1) {
                break;
            }

            TaskWaitResult waitResult = checkWaitResult();
            if (waitResult.stopWait()) {
                return waitResult;
            }

            this.wakeupLock.waiting(timeout, e -> {
                this.logger.error(getInterruptedMsg(), e);
                close();
            });

            waitResult = checkWaitResult();
            if (waitResult.stopWait()) {
                return waitResult;
            }
        }

        return checkWaitResult();
    }

    @Override
    protected void doClose() {
        this.executorService.shutdownNow();
        this.wakeupLock.wakeup();
    }

    @Override
    public boolean isParallelMode() {
        return true;
    }

    private TaskWaitResult checkWaitResult() {
        TaskExecuteResult stopFlag = super.getStateFlag();
        if (stopFlag.isExecuted()) {
            return this.currentTaskCount.get() == 0 ? TaskWaitResult.FINISH : TaskWaitResult.TIMEOUT;
        } else if (stopFlag.isClosed()) {
            return TaskWaitResult.CLOSE;
        } else if (stopFlag.isInterrupt()) {
            return TaskWaitResult.INTERRUPTED;
        } else {
            throw new IllegalStateException("unknown stopFlag: " + stopFlag);
        }
    }

    private String getInterruptedMsg() {
        Tracked tracked = super.getTracked();
        return tracked.say("interrupt and finish job");
    }

    public long getWaitRestUnitMs() {
        return waitRestUnitMs;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

}
