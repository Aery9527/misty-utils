package org.misty.utils.task.executor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.Watcher;
import org.misty.utils.ex.ThreadEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskWaitResult;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TaskSerialExecutorTest extends TaskBaseExecutorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public TaskSerialExecutor build() {
        return (TaskSerialExecutor) TaskExecutorBuilder.create().withSerial().build();
    }

    @Test
    public void isSerialMode_isParallelMode() {
        super.isSerialMode_isParallelMode(build());
    }

    @Test
    public void run() throws Exception {
        TaskSerialExecutor taskExecutor = build();

        AtomicReference<Thread> threadRef1 = new AtomicReference<>();
        AtomicReference<Thread> threadRef2 = new AtomicReference<>();
        AtomicReference<Thread> threadRef3 = new AtomicReference<>();

        TaskExecuteAction taskExecuteAction = Mockito.mock(TaskExecuteAction.class);
        Mockito.doAnswer(invocation -> {
            threadRef1.set(Thread.currentThread());
            return null;
        }).when(taskExecuteAction).execute();

        taskExecutor.run(taskExecuteAction);
        taskExecutor.run(() -> threadRef2.set(Thread.currentThread()));
        taskExecutor.run(() -> threadRef3.set(Thread.currentThread()), e -> null);

        Assertions.assertThat(threadRef1.get()).isEqualTo(Thread.currentThread());
        Assertions.assertThat(threadRef2.get()).isEqualTo(Thread.currentThread());
        Assertions.assertThat(threadRef3.get()).isEqualTo(Thread.currentThread());
    }

    @Test
    public void run_TaskExecuteResult_TaskErrorPolicy() {
        {
            String stageMsg = "test throw exception and errorHandler return TaskErrorPolicy.CONTINUE";
            this.logger.info(stageMsg);

            TaskSerialExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> TaskErrorPolicy.CONTINUE);

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> TaskErrorPolicy.INTERRUPT);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isTrue();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.EXECUTED);
        }

        {
            String stageMsg = "test throw exception and errorHandler return TaskErrorPolicy.INTERRUPT";
            this.logger.info(stageMsg);

            TaskSerialExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> TaskErrorPolicy.INTERRUPT);

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> TaskErrorPolicy.CONTINUE);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.INTERRUPTED);
        }

        {
            String stageMsg = "test throw exception and errorHandler return null (same to errorHandler throw error)";
            this.logger.info(stageMsg);

            TaskSerialExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> null);

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> null);

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.INTERRUPTED);
        }

        {
            String stageMsg = "test throw exception and errorHandler throw error (same to return null)";
            this.logger.info(stageMsg);

            TaskSerialExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            }, e -> {
                throw new RuntimeException(stageMsg);
            });

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
            }, e -> {
                throw new RuntimeException(stageMsg);
            });

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.INTERRUPTED);
        }

        { // test close
            String stageMsg = "test close";
            this.logger.info(stageMsg);

            TaskSerialExecutor taskExecutor = build();

            AtomicBoolean check1 = new AtomicBoolean(false);
            AtomicBoolean check2 = new AtomicBoolean(false);

            TaskExecuteResult result1 = taskExecutor.run(() -> {
                check1.set(true);
                throw new RuntimeException(stageMsg);
            });

            taskExecutor.close();

            TaskExecuteResult result2 = taskExecutor.run(() -> {
                check2.set(true);
                throw new RuntimeException(stageMsg);
            });

            Assertions.assertThat(check1.get()).isTrue();
            Assertions.assertThat(check2.get()).isFalse();
            Assertions.assertThat(result1).isEqualTo(TaskExecuteResult.EXECUTED);
            Assertions.assertThat(result2).isEqualTo(TaskExecuteResult.CLOSED);
        }
    }

    @Test
    public void waitFinish_TaskWaitResult() {
        long simulateProcessMs = 20;
        Duration waitTimeout = Duration.ofMillis(20);

        TaskSerialExecutor taskExecutor = build();

        taskExecutor.run(() -> ThreadEx.rest(simulateProcessMs));
        taskExecutor.run(() -> ThreadEx.rest(simulateProcessMs * 2));

        TaskWaitResult waitResult1 = taskExecutor.waitFinish(waitTimeout);

        ThreadEx.rest(simulateProcessMs);
        TaskWaitResult waitResult2 = taskExecutor.waitFinish(waitTimeout);

        taskExecutor.run(() -> {
            throw new RuntimeException("");
        }, e -> TaskErrorPolicy.INTERRUPT);
        Watcher watcher = Watcher.create();
        TaskWaitResult waitResult3 = taskExecutor.waitFinish();
        TaskWaitResult waitResult4 = taskExecutor.waitFinish(Duration.ofMillis(simulateProcessMs));
        long through1 = watcher.through();

        taskExecutor = build();
        taskExecutor.close();
        AtomicBoolean checkClose = new AtomicBoolean(false);
        taskExecutor.run(() -> checkClose.set(true));
        watcher.reset();
        TaskWaitResult waitResult5 = taskExecutor.waitFinish();
        TaskWaitResult waitResult6 = taskExecutor.waitFinish(Duration.ofMillis(simulateProcessMs));
        long through2 = watcher.through();

        Assertions.assertThat(waitResult1).isEqualTo(TaskWaitResult.FINISH);
        Assertions.assertThat(waitResult2).isEqualTo(TaskWaitResult.FINISH);
        Assertions.assertThat(waitResult3).isEqualTo(TaskWaitResult.INTERRUPTED);
        Assertions.assertThat(waitResult4).isEqualTo(TaskWaitResult.INTERRUPTED);
        Assertions.assertThat(through1).isLessThanOrEqualTo(simulateProcessMs);
        Assertions.assertThat(checkClose.get()).isFalse();
        Assertions.assertThat(waitResult5).isEqualTo(TaskWaitResult.CLOSE);
        Assertions.assertThat(waitResult6).isEqualTo(TaskWaitResult.CLOSE);
        Assertions.assertThat(through2).isLessThanOrEqualTo(simulateProcessMs);
    }

}
