package org.misty.utils.task.dispatcher;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskGiveResult;
import org.misty.utils.task.executor.TaskExecutorBuilder;
import org.misty.utils.task.executor.TaskParallelExecutor;
import org.misty.utils.task.executor.TaskSerialExecutor;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class TaskDispatcherBuilderTest {

    private static <Type> TaskDispatcherBuilder<Type> getBuilder() {
        TaskDispatcherBuilder<Type> builder = TaskDispatcher.builder();
        return builder.withSerial(); // 採用同步操作, 避免非同步造成順序錯誤問題
    }

    @Test
    public void constructor() {
        AssertionsEx.awareThrown(() -> TaskDispatcherBuilder.create(null)).isInstanceOf(IllegalArgumentException.class);

        TaskDispatcherBuilder<String> builder = TaskDispatcherBuilder.create(Tracked.create());

        TaskExecutorBuilder taskExecutorBuilder = builder.getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.build() instanceof TaskParallelExecutor).isTrue();

        ConsumerEx<TaskGiveResult<String>> noMatchedAction = builder.getNoMatchedAction();
        noMatchedAction.execute(new TaskGiveResult<>("kerker", 0, Collections.emptyMap()));
    }

    @Test
    public void giveAction_taskReceiver() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        AtomicInteger check = new AtomicInteger(0);
        builder.giveAction(take -> check.incrementAndGet());

        TaskDispatcher<String> taskDispatcher = builder.build();
        taskDispatcher.give("kerker");

        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

    @Test
    public void giveAction_acceptClass_taskReceiver() {
        TaskDispatcherBuilder<Number> builder = getBuilder();

        AtomicInteger check = new AtomicInteger(0);
        builder.giveAction(Integer.class, take -> check.incrementAndGet());

        TaskDispatcher<Number> taskDispatcher = builder.build();

        taskDispatcher.give(0L);
        AssertionsEx.assertThat(check.get()).isEqualTo(0);

        taskDispatcher.give(0);
        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

    @Test
    public void giveAction_accept_taskReceiver() {
        TaskDispatcherBuilder<Object> builder = getBuilder();

        Object object = new Object();

        AtomicInteger check = new AtomicInteger(0);
        builder.giveAction(task -> task == object, take -> check.incrementAndGet());

        TaskDispatcher<Object> taskDispatcher = builder.build();

        taskDispatcher.give("");
        AssertionsEx.assertThat(check.get()).isEqualTo(0);

        taskDispatcher.give(object);
        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

    @Test
    public void giveAction_taskReceiver_errorHandler() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        AtomicBoolean check = new AtomicBoolean(false);
        builder.giveAction(take -> {
            throw new RuntimeException();
        }, (task, e) -> {
            check.set(true);
            return TaskErrorPolicy.CONTINUE;
        });

        TaskDispatcher<String> taskDispatcher = builder.build();
        taskDispatcher.give("kerker");

        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void giveAction_acceptClass_taskReceiver_errorHandler() {
        TaskDispatcherBuilder<Number> builder = getBuilder();

        AtomicBoolean check = new AtomicBoolean(false);
        builder.giveAction(Integer.class, take -> {
            throw new RuntimeException();
        }, (task, e) -> {
            check.set(true);
            return TaskErrorPolicy.CONTINUE;
        });

        TaskDispatcher<Number> taskDispatcher = builder.build();

        taskDispatcher.give(0L);
        AssertionsEx.assertThat(check).isFalse();

        taskDispatcher.give(0);
        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void giveAction_accept_taskReceiver_errorHandler() {
        TaskDispatcherBuilder<Object> builder = getBuilder();

        Object object = new Object();

        AtomicBoolean check = new AtomicBoolean(false);
        builder.giveAction(task -> task == object, take -> {
            throw new RuntimeException();
        }, (task, e) -> {
            check.set(true);
            return TaskErrorPolicy.CONTINUE;
        });

        TaskDispatcher<Object> taskDispatcher = builder.build();

        taskDispatcher.give("");
        AssertionsEx.assertThat(check).isFalse();

        taskDispatcher.give(object);
        AssertionsEx.assertThat(check).isTrue();
    }

    @Test
    public void giveAction_taskDispatchAction() throws Exception {
        TaskDispatchAction<String> taskDispatchAction = Mockito.mock(TaskDispatchAction.class);

        AtomicReference<String> check1 = new AtomicReference<>();
        AtomicReference<String> check2 = new AtomicReference<>();
        AtomicReference<String> check3 = new AtomicReference<>();
        AtomicReference<Exception> check4 = new AtomicReference<>();
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
            return TaskErrorPolicy.CONTINUE;
        }).when(taskDispatchAction).handleError(Mockito.any(), Mockito.any());

        TaskDispatcherBuilder<String> builder = getBuilder();
        TaskDispatcher<String> taskDispatcher = builder.giveAction(taskDispatchAction).build();

        taskDispatcher.give("kerker");

        AssertionsEx.assertThat(check1.get()).isEqualTo("kerker");
        AssertionsEx.assertThat(check2.get()).isEqualTo("kerker");
        AssertionsEx.assertThat(check3.get()).isEqualTo("kerker");
        AssertionsEx.assertThat(check4.get()).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void withSerial_withParallel() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        TaskExecutorBuilder taskExecutorBuilder = builder.getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.build()).isInstanceOf(TaskSerialExecutor.class);

        taskExecutorBuilder = builder.withParallel().getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.build()).isInstanceOf(TaskParallelExecutor.class);

        taskExecutorBuilder = builder.withParallel(123).getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.build()).isInstanceOf(TaskParallelExecutor.class);

        taskExecutorBuilder = builder.withParallel(new LinkedBlockingDeque<>()).getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.build()).isInstanceOf(TaskParallelExecutor.class);

        taskExecutorBuilder = builder.withParallel(Executors.newFixedThreadPool(321)).getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.build()).isInstanceOf(TaskParallelExecutor.class);
    }

    @Test
    public void giveNoMatchedAction() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        builder.giveAction("9527"::equals, take -> {
        });

        AtomicReference<TaskGiveResult<String>> check = new AtomicReference<>();
        TaskDispatcher<String> taskDispatcher = builder.giveNoMatchedAction(check::set).build();

        taskDispatcher.give("5566");

        AssertionsEx.assertThat(check.get()).isNotNull();
    }

    @Test
    public void giveDefaultErrorHandler() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        builder.giveAction(take -> {
            throw new RuntimeException();
        });

        AtomicInteger check = new AtomicInteger(0);
        builder.giveDefaultErrorHandler((task, e) -> {
            check.incrementAndGet();
            return TaskErrorPolicy.CONTINUE;
        });

        builder.giveAction(take -> {
            throw new RuntimeException();
        });

        TaskDispatcher<String> taskDispatcher = builder.build();
        taskDispatcher.give("kerker");

        AssertionsEx.assertThat(check.get()).isEqualTo(2);
    }

    @Test
    public void build() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        AssertionsEx.awareThrown(builder::build).isInstanceOf(IllegalStateException.class);

        builder.giveAction(take -> {
        });

        AssertionsEx.assertThat(builder.build()).isNotNull();
    }

    @Test
    public void checkBuildFlag() {
        TaskDispatchAction<String> taskDispatchAction = Mockito.mock(TaskDispatchAction.class);

        TaskDispatcherBuilder<String> builder = getBuilder();

        builder.giveAction(take -> {
        });

        builder.build();

        Consumer<Runnable> test = runnable -> {
            AssertionsEx.awareThrown(runnable::run).isInstanceOf(IllegalStateException.class);
        };

        test.accept(() -> builder.giveAction(task -> {
        }));
        test.accept(() -> builder.giveAction(String.class, task -> {
        }));
        test.accept(() -> builder.giveAction(task -> true, task -> {
        }));
        test.accept(() -> builder.giveAction(task -> {
        }, (task, e) -> TaskErrorPolicy.CONTINUE));
        test.accept(() -> builder.giveAction(String.class, task -> {
        }, (task, e) -> TaskErrorPolicy.CONTINUE));
        test.accept(() -> builder.giveAction(task -> true, task -> {
        }, (task, e) -> TaskErrorPolicy.CONTINUE));
        test.accept(() -> builder.giveAction(taskDispatchAction));
        test.accept(builder::withSerial);
        test.accept(builder::withParallel);
        test.accept(() -> builder.withParallel(123));
        test.accept(() -> builder.withParallel(new LinkedBlockingDeque<>()));
        test.accept(() -> builder.withParallel(Executors.newFixedThreadPool(321)));
        test.accept(() -> builder.giveNoMatchedAction(taskGiveResult -> {
        }));
        test.accept(() -> builder.giveDefaultErrorHandler((task, e) -> TaskErrorPolicy.CONTINUE));
    }

}
