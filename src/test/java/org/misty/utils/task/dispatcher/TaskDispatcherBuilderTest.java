package org.misty.utils.task.dispatcher;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.Tracked;
import org.misty.utils.task.executor.TaskExecutor;
import org.misty.utils.task.executor.TaskParallelExecutor;
import org.misty.utils.task.executor.TaskSerialExecutor;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TaskDispatcherBuilderTest {

    private static <Type> TaskDispatcherBuilder<Type> getBuilder() {
        TaskDispatcherBuilder<Type> builder = TaskDispatcher.builder("kereker");
        return builder.withSerial(); // 採用同步操作, 避免非同步造成順序錯誤問題
    }

    @Test
    public void constructor() {
        AssertionsEx.awareThrown(() -> TaskDispatcherBuilder.create(null)).isInstanceOf(IllegalArgumentException.class);

        TaskDispatcherBuilder<String> builder = TaskDispatcherBuilder.create(Tracked.create());

        Supplier<TaskExecutor> taskExecutorBuilder = builder.getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.get() instanceof TaskParallelExecutor).isTrue();
    }

    @Test
    public void giveAction() throws Exception {
        TaskDispatcherBuilder<Number> builder = getBuilder();

        int index = 0;

        { // taskReceiver
            AtomicBoolean check = new AtomicBoolean();
            builder.giveAction(take -> check.set(true));

            TaskDispatchAction<Number> action = builder.getTaskDispatchActionList().get(index++);
            action.receive(1);

            AssertionsEx.assertThat(action).isInstanceOf(TaskDispatchActionAdapter.class);
            AssertionsEx.assertThat(action.accept(null)).isTrue();
            AssertionsEx.assertThat(check).isTrue();
        }

        { // acceptClass + taskReceiver
            AtomicBoolean check = new AtomicBoolean();
            builder.giveAction(Integer.class, take -> check.set(true));

            TaskDispatchAction<Number> action = builder.getTaskDispatchActionList().get(index++);
            action.receive(1);

            AssertionsEx.assertThat(action).isInstanceOf(TaskDispatchActionAdapter.class);
            AssertionsEx.assertThat(action.accept(1)).isTrue();
            AssertionsEx.assertThat(action.accept(1L)).isFalse();
            AssertionsEx.assertThat(check).isTrue();
        }

        { // accept + taskReceiver
            AtomicBoolean check = new AtomicBoolean();
            builder.giveAction(task -> task.equals(1), take -> check.set(true));

            TaskDispatchAction<Number> action = builder.getTaskDispatchActionList().get(index++);
            action.receive(1);

            AssertionsEx.assertThat(action).isInstanceOf(TaskDispatchActionAdapter.class);
            AssertionsEx.assertThat(action.accept(1)).isTrue();
            AssertionsEx.assertThat(action.accept(1L)).isFalse();
            AssertionsEx.assertThat(check).isTrue();
        }

        { // taskReceiver + errorHandler
            AtomicBoolean check1 = new AtomicBoolean();
            AtomicBoolean check2 = new AtomicBoolean();
            builder.giveAction(take -> check1.set(true), (actionTracked, task, e) -> check2.set(true));

            TaskDispatchAction<Number> action = builder.getTaskDispatchActionList().get(index++);
            action.receive(1);
            action.handleError(action.getTracked(), 1, new RuntimeException());

            AssertionsEx.assertThat(action).isInstanceOf(TaskDispatchActionAdapter.class);
            AssertionsEx.assertThat(action.accept(null)).isTrue();
            AssertionsEx.assertThat(check1).isTrue();
            AssertionsEx.assertThat(check2).isTrue();
        }

        { // acceptClass + taskReceiver + errorHandler
            AtomicBoolean check1 = new AtomicBoolean();
            AtomicBoolean check2 = new AtomicBoolean();
            builder.giveAction(Long.class, take -> check1.set(true), (actionTracked, task, e) -> check2.set(true));

            TaskDispatchAction<Number> action = builder.getTaskDispatchActionList().get(index++);
            action.receive(1);
            action.handleError(action.getTracked(), 1, new RuntimeException());

            AssertionsEx.assertThat(action).isInstanceOf(TaskDispatchActionAdapter.class);
            AssertionsEx.assertThat(action.accept(1)).isFalse();
            AssertionsEx.assertThat(action.accept(1L)).isTrue();
            AssertionsEx.assertThat(check1).isTrue();
            AssertionsEx.assertThat(check2).isTrue();
        }

        { // accept + taskReceiver + errorHandler
            AtomicBoolean check1 = new AtomicBoolean();
            AtomicBoolean check2 = new AtomicBoolean();
            builder.giveAction(task -> task.equals(2L), take -> check1.set(true), (actionTracked, task, e) -> check2.set(true));

            TaskDispatchAction<Number> action = builder.getTaskDispatchActionList().get(index++);
            action.receive(1);
            action.handleError(action.getTracked(), 1, new RuntimeException());

            AssertionsEx.assertThat(action).isInstanceOf(TaskDispatchActionAdapter.class);
            AssertionsEx.assertThat(action.accept(2)).isFalse();
            AssertionsEx.assertThat(action.accept(2L)).isTrue();
            AssertionsEx.assertThat(check1).isTrue();
            AssertionsEx.assertThat(check2).isTrue();
        }
    }

    @Test
    public void withSerial_withOrdered_withDisordered() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        Supplier<TaskExecutor> taskExecutorBuilder = builder.withSerial().getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.get()).isInstanceOf(TaskSerialExecutor.class);

        taskExecutorBuilder = builder.giveAction(task -> { // ordered會採用action數量作為Executor的thread number, 所以至少要給一個action
        }).withOrdered().getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.get()).isInstanceOf(TaskParallelExecutor.class);

        taskExecutorBuilder = builder.withDisordered(123).getTaskExecutorBuilder();
        AssertionsEx.assertThat(taskExecutorBuilder.get()).isInstanceOf(TaskParallelExecutor.class);
    }

    @Test
    public void giveDefaultErrorHandler() {
        TaskDispatcherBuilder<String> builder = getBuilder();

        builder.giveAction(take -> {
            throw new RuntimeException();
        });

        AtomicInteger check = new AtomicInteger(0);
        builder.giveDefaultErrorHandler((actionTracked, task, e) -> {
            check.incrementAndGet();
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

        Supplier<TaskDispatcherBuilder> builderSupplier = () -> TaskDispatcher.builder().giveAction(take -> {
        });
        AssertionsEx.assertThat(builderSupplier.get().withSerial().build()).isInstanceOf(TaskSerialDispatcher.class);
        AssertionsEx.assertThat(builderSupplier.get().withOrdered().build()).isInstanceOf(TaskOrderedDispatcher.class);
        AssertionsEx.assertThat(builderSupplier.get().withDisordered().build()).isInstanceOf(TaskDisorderedDispatcher.class);
        AssertionsEx.assertThat(builderSupplier.get().withDisordered(123).build()).isInstanceOf(TaskDisorderedDispatcher.class);
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
        }, (actionTracked, task, e) -> {
        }));
        test.accept(() -> builder.giveAction(String.class, task -> {
        }, (actionTracked, task, e) -> {
        }));
        test.accept(() -> builder.giveAction(task -> true, task -> {
        }, (actionTracked, task, e) -> {
        }));
        test.accept(() -> builder.giveAction(taskDispatchAction));
        test.accept(builder::withSerial);
        test.accept(builder::withOrdered);
        test.accept(builder::withDisordered);
        test.accept(() -> builder.withDisordered(123));
        test.accept(() -> builder.giveDefaultErrorHandler((actionTracked, task, e) -> {
        }));
    }

}
