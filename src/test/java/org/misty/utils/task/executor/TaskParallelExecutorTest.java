package org.misty.utils.task.executor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.Watcher;
import org.misty.utils.ex.CountDownLatchEx;
import org.misty.utils.ex.ThreadEx;
import org.misty.utils.lock.GroupWakeupLock;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskWaitResult;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TaskParallelExecutorTest extends TaskBaseExecutorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static TaskParallelExecutor build() {
        return (TaskParallelExecutor) TaskExecutor.builder().withParallel().build();
    }

    @Test
    public void isSerialMode_isParallelMode() {
        super.isSerialMode_isParallelMode(build());
    }

    @Test
    public void run() throws Exception {
        TaskParallelExecutor taskExecutor = build();

        AtomicReference<Thread> threadRef1 = new AtomicReference<>();
        AtomicReference<Thread> threadRef2 = new AtomicReference<>();
        AtomicReference<Thread> threadRef3 = new AtomicReference<>();
        CountDownLatchEx latch = new CountDownLatchEx(3);

        TaskExecuteAction taskExecuteAction = Mockito.mock(TaskExecuteAction.class);
        Mockito.doAnswer(invocation -> {
            threadRef1.set(Thread.currentThread());
            latch.countDown();
            return null;
        }).when(taskExecuteAction).execute();

        taskExecutor.run(taskExecuteAction);
        taskExecutor.run(() -> {
            threadRef2.set(Thread.currentThread());
            latch.countDown();
        });
        taskExecutor.run(() -> {
            threadRef3.set(Thread.currentThread());
            latch.countDown();
        }, e -> null);

        latch.wating();

        Assertions.assertThat(threadRef1.get()).isNotEqualTo(Thread.currentThread());
        Assertions.assertThat(threadRef2.get()).isNotEqualTo(Thread.currentThread());
        Assertions.assertThat(threadRef3.get()).isNotEqualTo(Thread.currentThread());
    }

    @Test
    public void run_TaskExecuteResult_TaskErrorPolicy() {
        long simulateProcessMs = 20;

        {
            String stageMsg = "test throw exception and errorHandler return TaskErrorPolicy.CONTINUE";
            this.logger.info(stageMsg);

            TaskParallelExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> TaskErrorPolicy.CONTINUE);
            ThreadEx.rest(simulateProcessMs);

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> TaskErrorPolicy.INTERRUPT);
            ThreadEx.rest(simulateProcessMs);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isTrue();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.EXECUTED);
        }

        {
            String stageMsg = "test throw exception and errorHandler return TaskErrorPolicy.INTERRUPT";
            this.logger.info(stageMsg);

            TaskParallelExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> TaskErrorPolicy.INTERRUPT);
            ThreadEx.rest(simulateProcessMs);

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> TaskErrorPolicy.CONTINUE);
            ThreadEx.rest(simulateProcessMs);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.INTERRUPTED);
        }

        {
            String stageMsg = "test throw exception and errorHandler return null (same to errorHandler throw error)";
            this.logger.info(stageMsg);
            TaskParallelExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> null);
            ThreadEx.rest(simulateProcessMs);

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> null);
            ThreadEx.rest(simulateProcessMs);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.INTERRUPTED);
        }

        {
            String stageMsg = "test throw exception and errorHandler throw error (same to return null)";
            this.logger.info(stageMsg);

            TaskParallelExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> {
                throw new RuntimeException(stageMsg);
            });
            ThreadEx.rest(simulateProcessMs);

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> {
                throw new RuntimeException(stageMsg);
            });
            ThreadEx.rest(simulateProcessMs);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.INTERRUPTED);
        }

        { // test close
            String stageMsg = "test close";
            this.logger.info(stageMsg);

            TaskParallelExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            });
            ThreadEx.rest(simulateProcessMs);

            taskExecutor.close();

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
                throw new RuntimeException(stageMsg);
            });
            ThreadEx.rest(simulateProcessMs);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.CLOSED);
        }
    }

    @Test
    public void waitFinish_TaskWaitResult() {
        long simulateProcessMs = 20;
        Duration waitTimeout = Duration.ofMillis(simulateProcessMs);

        String key1 = "a", key2 = "b";
        GroupWakeupLock groupWakeupLock = new GroupWakeupLock();

        TaskParallelExecutor taskExecutor = build();

        taskExecutor.run(() -> groupWakeupLock.waiting(key1));
        taskExecutor.run(() -> groupWakeupLock.waiting(key2));

        TaskWaitResult waitResult1 = taskExecutor.waitFinish(waitTimeout);

        groupWakeupLock.wakeup(key1);
        TaskWaitResult waitResult2 = taskExecutor.waitFinish(waitTimeout);

        groupWakeupLock.wakeup(key2);
        TaskWaitResult waitResult3 = taskExecutor.waitFinish(waitTimeout);

        taskExecutor.run(() -> ThreadEx.rest(simulateProcessMs));
        TaskWaitResult waitResult4 = taskExecutor.waitFinish();

        taskExecutor.run(() -> {
            throw new RuntimeException("");
        }, e -> TaskErrorPolicy.INTERRUPT);
        ThreadEx.rest(simulateProcessMs);
        Watcher watcher = Watcher.create();
        TaskWaitResult waitResult5 = taskExecutor.waitFinish();
        TaskWaitResult waitResult6 = taskExecutor.waitFinish(Duration.ofMillis(simulateProcessMs));
        long through1 = watcher.through();

        taskExecutor = build();
        taskExecutor.close();
        AtomicBoolean checkClose = new AtomicBoolean(false);
        taskExecutor.run(() -> checkClose.set(true));
        ThreadEx.rest(simulateProcessMs);
        watcher.reset();
        TaskWaitResult waitResult7 = taskExecutor.waitFinish();
        TaskWaitResult waitResult8 = taskExecutor.waitFinish(Duration.ofMillis(simulateProcessMs));
        long through2 = watcher.through();

        Assertions.assertThat(waitResult1).isEqualTo(TaskWaitResult.TIMEOUT);
        Assertions.assertThat(waitResult2).isEqualTo(TaskWaitResult.TIMEOUT);
        Assertions.assertThat(waitResult3).isEqualTo(TaskWaitResult.FINISH);
        Assertions.assertThat(waitResult4).isEqualTo(TaskWaitResult.FINISH);
        Assertions.assertThat(waitResult5).isEqualTo(TaskWaitResult.INTERRUPTED);
        Assertions.assertThat(waitResult6).isEqualTo(TaskWaitResult.INTERRUPTED);
        Assertions.assertThat(through1).isLessThanOrEqualTo(simulateProcessMs);
        Assertions.assertThat(checkClose.get()).isFalse();
        Assertions.assertThat(waitResult7).isEqualTo(TaskWaitResult.CLOSE);
        Assertions.assertThat(waitResult8).isEqualTo(TaskWaitResult.CLOSE);
        Assertions.assertThat(through2).isLessThanOrEqualTo(simulateProcessMs);
    }

}
