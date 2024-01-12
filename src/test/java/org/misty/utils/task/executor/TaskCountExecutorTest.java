package org.misty.utils.task.executor;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.Tracked;
import org.misty.utils.fi.LongConsumerEx;
import org.misty.utils.fi.RunnableEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

public class TaskCountExecutorTest {

    private final TaskExecutor taskExecutor = Mockito.mock(TaskExecutor.class);

    private TaskCountExecutor executor;

    public TaskCountExecutorTest() {
        Mockito.when(this.taskExecutor.getTracked()).thenReturn(Tracked.create());
        this.executor = new TaskCountExecutor(taskExecutor);
    }

    @Test
    public void run_TaskExecuteAction() {
        TaskCountExecuteAction action = Mockito.mock(TaskCountExecuteAction.class);

        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).run(Mockito.any(), Mockito.any());

        this.executor.run(action);

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void run_LongConsumerEx() {
        LongConsumerEx action = times -> {
        };

        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).run((RunnableEx) Mockito.any());

        this.executor.run(action);

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void run_LongConsumerEx_BiFunction() {
        LongConsumerEx action = times -> {
        };
        BiFunction<Long, Exception, TaskErrorPolicy> errorHandler = (times, e) -> TaskErrorPolicy.CONTINUE;

        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).run(Mockito.any(), Mockito.any());

        this.executor.run(action, errorHandler);

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void waitFinish() {
        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).waitFinish();

        this.executor.waitFinish();

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void waitFinish_waitTime() {
        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).waitFinish(Mockito.any());

        this.executor.waitFinish(Mockito.any());

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void isSerialMode() {
        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).isSerialMode();

        this.executor.isSerialMode();

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void isParallelMode() {
        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).isParallelMode();

        this.executor.isParallelMode();

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void getTracked() {
        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).getTracked();

        this.executor.getTracked();

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void close() throws Exception {
        AtomicBoolean check = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check.set(true);
            return null;
        }).when(this.taskExecutor).close();

        this.executor.close();

        AssertionsEx.assertThat(check).isTrue();
    }

}
