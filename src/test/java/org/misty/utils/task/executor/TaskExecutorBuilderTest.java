package org.misty.utils.task.executor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.task.TaskErrorPolicy;

import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public class TaskExecutorBuilderTest {

    @Test
    public void withSerial() {
        Assertions.assertThat(TaskExecutorBuilder.create().withSerial().build()).isInstanceOf(TaskSerialExecutor.class);
    }

    @Test
    public void withParallel() {
        Assertions.assertThat(TaskExecutorBuilder.create().withParallel().build()).isInstanceOf(TaskParallelExecutor.class);
        Assertions.assertThat(TaskExecutorBuilder.create().withParallel(3).build()).isInstanceOf(TaskParallelExecutor.class);
        Assertions.assertThat(TaskExecutorBuilder.create().withParallel(Executors.newSingleThreadExecutor()).build()).isInstanceOf(TaskParallelExecutor.class);
    }

    @Test
    public void giveDefaultErrorHandler() {
        Function<Exception, TaskErrorPolicy> defaultErrorHandler = error -> TaskErrorPolicy.CONTINUE;

        Consumer<TaskExecutorBuilder> consumer = builder -> {
            TaskExecutor executor = builder.giveDefaultErrorHandler(defaultErrorHandler).build();
            TaskBaseExecutor baseExecutor = (TaskBaseExecutor) executor;
            Assertions.assertThat(baseExecutor.getDefaultErrorHandler() == defaultErrorHandler).isTrue();
        };

        consumer.accept(TaskExecutorBuilder.create().withSerial());
        consumer.accept(TaskExecutorBuilder.create().withParallel());
        consumer.accept(TaskExecutorBuilder.create().withParallel(3));
        consumer.accept(TaskExecutorBuilder.create().withParallel(Executors.newSingleThreadExecutor()));
    }

}
