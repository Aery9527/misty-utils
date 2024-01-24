package org.misty.utils.task.dispatcher;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.Tracked;
import org.misty.utils.ex.CountDownLatchEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.executor.TaskExecutor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class TaskBaseDispatcherTest {

    public abstract <Task> TaskDispatcherBuilder<Task> builderSetting(TaskDispatcherBuilder<Task> builder);

    @Test
    public void multi_action() {
        IntStream.rangeClosed(1, 10).forEach(actionCount -> {
            TaskDispatcherBuilder<String> builder = builderSetting(TaskDispatcher.builder());

            List<Boolean> check = new ArrayList<>(actionCount);
            IntStream.range(0, actionCount).forEach(i -> check.add(false));

            CountDownLatchEx latch = new CountDownLatchEx(actionCount);

            for (int i = 0; i < actionCount; i++) {
                int times = i;
                builder.giveAction(task -> {
                    check.set(times, true);
                    latch.countDown();
                });
            }

            TaskDispatcher<String> taskDispatcher = builder.build();
            taskDispatcher.give("kerker");

            latch.wating();

            AssertionsEx.assertThat(check).allMatch(b -> b);
        });
    }

    @Test
    public void giveAction_taskReceiver() {
        TaskDispatcherBuilder<String> builder = builderSetting(TaskDispatcher.builder());

        CountDownLatchEx latch = new CountDownLatchEx(1);

        AtomicInteger check = new AtomicInteger(0);
        builder.giveAction(take -> {
            check.incrementAndGet();
            latch.countDown();
        });

        TaskDispatcher<String> taskDispatcher = builder.build();
        taskDispatcher.give("kerker");

        latch.wating();

        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

    @Test
    public void giveAction_acceptClass_taskReceiver() {
        TaskDispatcherBuilder<Number> builder = builderSetting(TaskDispatcher.builder());

        CountDownLatchEx latch = new CountDownLatchEx(1);

        AtomicInteger check = new AtomicInteger(0);
        builder.giveAction(Integer.class, take -> {
            check.incrementAndGet();
            latch.countDown();
        });

        TaskDispatcher<Number> taskDispatcher = builder.build();

        taskDispatcher.give(0L);
        AssertionsEx.assertThat(check.get()).isEqualTo(0);

        taskDispatcher.give(0);
        latch.wating();
        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

    @Test
    public void giveAction_accept_taskReceiver() {
        TaskDispatcherBuilder<Object> builder = builderSetting(TaskDispatcher.builder());

        CountDownLatchEx latch = new CountDownLatchEx(1);
        Object object = new Object();

        AtomicInteger check = new AtomicInteger(0);
        builder.giveAction(task -> task == object, take -> {
            check.incrementAndGet();
            latch.countDown();
        });

        TaskDispatcher<Object> taskDispatcher = builder.build();

        taskDispatcher.give("");
        AssertionsEx.assertThat(check.get()).isEqualTo(0);

        taskDispatcher.give(object);
        latch.wating();
        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

    @Test
    public void giveAction_taskReceiver_errorHandler() {
        TaskDispatcherBuilder<String> builder = builderSetting(TaskDispatcher.builder());

        CountDownLatchEx latch = new CountDownLatchEx(1);

        AtomicBoolean check = new AtomicBoolean(false);
        builder.giveAction(take -> {
            throw new RuntimeException();
        }, (actionTracked, task, e) -> {
            check.set(true);
            latch.countDown();
        });

        TaskDispatcher<String> taskDispatcher = builder.build();
        taskDispatcher.give("kerker");
        latch.wating();

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void giveAction_acceptClass_taskReceiver_errorHandler() {
        TaskDispatcherBuilder<Number> builder = builderSetting(TaskDispatcher.builder());

        CountDownLatchEx latch = new CountDownLatchEx(1);

        AtomicBoolean check = new AtomicBoolean(false);
        builder.giveAction(Integer.class, take -> {
            throw new RuntimeException();
        }, (actionTracked, task, e) -> {
            check.set(true);
            latch.countDown();
        });

        TaskDispatcher<Number> taskDispatcher = builder.build();

        taskDispatcher.give(0L);
        AssertionsEx.assertThat(check).isFalse();

        taskDispatcher.give(0);
        latch.wating();
        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void giveAction_accept_taskReceiver_errorHandler() {
        TaskDispatcherBuilder<Object> builder = builderSetting(TaskDispatcher.builder());

        CountDownLatchEx latch = new CountDownLatchEx(1);
        Object object = new Object();

        AtomicBoolean check = new AtomicBoolean(false);
        builder.giveAction(task -> task == object, take -> {
            throw new RuntimeException();
        }, (actionTracked, task, e) -> {
            check.set(true);
            latch.countDown();
        });

        TaskDispatcher<Object> taskDispatcher = builder.build();

        taskDispatcher.give("");
        AssertionsEx.assertThat(check).isFalse();

        taskDispatcher.give(object);
        latch.wating();
        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void giveAction_taskDispatchAction() throws Exception {
        TaskDispatchAction<String> taskDispatchAction = Mockito.mock(TaskDispatchAction.class);

        CountDownLatchEx latch = new CountDownLatchEx(1);

        Tracked tracked = Tracked.create();
        Mockito.when(taskDispatchAction.getTracked()).thenReturn(tracked);

        AtomicReference<String> check1 = new AtomicReference<>();
        AtomicReference<String> check2 = new AtomicReference<>();
        AtomicReference<Tracked> check3 = new AtomicReference<>();
        AtomicReference<String> check4 = new AtomicReference<>();
        AtomicReference<Exception> check5 = new AtomicReference<>();
        Mockito.doAnswer(invocation -> {
            check1.set(invocation.getArgument(0));
            return true;
        }).when(taskDispatchAction).accept(Mockito.any());
        Mockito.doAnswer(invocation -> {
            check2.set(invocation.getArgument(0));
            throw new RuntimeException();
        }).when(taskDispatchAction).receive(Mockito.any());
        Mockito.doAnswer(invocation -> {
            check3.set(invocation.getArgument(0));
            check4.set(invocation.getArgument(1));
            check5.set(invocation.getArgument(2));
            latch.countDown();
            return TaskErrorPolicy.CONTINUE;
        }).when(taskDispatchAction).handleError(Mockito.any(), Mockito.any(), Mockito.any());

        TaskDispatcherBuilder<String> builder = builderSetting(TaskDispatcher.builder());
        TaskDispatcher<String> taskDispatcher = builder.giveAction(taskDispatchAction).build();

        taskDispatcher.give("kerker");
        latch.wating();

        AssertionsEx.assertThat(check1.get()).isEqualTo("kerker");
        AssertionsEx.assertThat(check2.get()).isEqualTo("kerker");
        AssertionsEx.assertThat(check3.get()).isEqualTo(tracked);
        AssertionsEx.assertThat(check4.get()).isEqualTo("kerker");
        AssertionsEx.assertThat(check5.get()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void close() {
        TaskDispatcherBuilder<String> builder = builderSetting(TaskDispatcher.builder());
        TaskDispatcher<String> taskDispatcher = builder.giveAction(task -> {
        }).build();

        AssertionsEx.assertThat(taskDispatcher.give("kerker")).isTrue();

        taskDispatcher.close();

        AssertionsEx.assertThat(taskDispatcher.give("kerker")).isFalse();
    }

    @Test
    public void waitAllTaskFinish_AND_close_PASS_TO_taskExecutor() {
        TaskDispatcherBuilder<String> taskDispatcherBuilder = Mockito.spy(builderSetting(TaskDispatcher.builder()));
        TaskExecutor taskExecutor = Mockito.mock(TaskExecutor.class);
        Supplier<TaskExecutor> taskExecutorBuilder = () -> taskExecutor;

        Mockito.when(taskDispatcherBuilder.getTaskExecutorBuilder()).thenReturn(taskExecutorBuilder);

        AtomicBoolean check1 = new AtomicBoolean(false);
        AtomicBoolean check2 = new AtomicBoolean(false);
        AtomicBoolean check3 = new AtomicBoolean(false);
        Mockito.doAnswer(invocation -> {
            check1.set(true);
            return null;
        }).when(taskExecutor).waitFinish();
        Mockito.doAnswer(invocation -> {
            check2.set(true);
            return null;
        }).when(taskExecutor).waitFinish(Mockito.any());
        Mockito.doAnswer(invocation -> {
            check3.set(true);
            return null;
        }).when(taskExecutor).close();

        TaskDispatcher<String> taskDispatcher = taskDispatcherBuilder.giveAction(task -> {
        }).build();

        taskDispatcher.waitAllTaskFinish();
        taskDispatcher.waitAllTaskFinish(Mockito.any());
        taskDispatcher.close();

        AssertionsEx.assertThat(check1.get()).isTrue();
        AssertionsEx.assertThat(check2.get()).isTrue();
        AssertionsEx.assertThat(check3.get()).isTrue();
    }

}
