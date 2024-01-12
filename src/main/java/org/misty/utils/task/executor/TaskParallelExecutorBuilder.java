package org.misty.utils.task.executor;

import org.misty.utils.limit.Limiter;
import org.misty.utils.limit.LongLimiter;
import org.misty.utils.verify.Verifier;

import java.util.concurrent.ExecutorService;

public class TaskParallelExecutorBuilder extends TaskBaseExecutorBuilder<TaskParallelExecutor, TaskParallelExecutorBuilder> {

    private final LongLimiter waitRestUnitMs = Limiter.longLimiterBuilder("waitRestUnitMs").giveLimit(10L, 1000L).build(100L);

    private ExecutorService executorService;

    @Override
    public TaskParallelExecutor doBuild() {
        Verifier.refuseNull("executorService", executorService);
        return new TaskParallelExecutor(this);
    }

    public TaskParallelExecutorBuilder giveWaitRestUnitMs(long waitRestUnitMs) {
        this.waitRestUnitMs.set(waitRestUnitMs);
        return this;
    }

    public TaskParallelExecutorBuilder giveExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public long getWaitRestUnitMs() {
        return waitRestUnitMs.get();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

}
