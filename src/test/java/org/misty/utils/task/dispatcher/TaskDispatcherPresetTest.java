package org.misty.utils.task.dispatcher;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskGiveResult;
import org.misty.utils.task.executor.TaskExecutor;
import org.misty.utils.task.executor.TaskExecutorBuilder;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class TaskDispatcherPresetTest {

    private static <Type> TaskDispatcherBuilder<Type> getBuilder() {
        TaskDispatcherBuilder<Type> builder = TaskDispatcher.builder();
        return builder.withSerial(); // 採用同步操作, 避免非同步造成順序錯誤問題
    }

    @Test
    public void multi_action() {
        IntStream.rangeClosed(1, 6).forEach(actionCount -> {
            TaskDispatcherBuilder<String> builder = getBuilder();

            List<Integer> check = new ArrayList<>();
            for (int i = 0; i < actionCount; i++) {
                int times = i;
                builder.giveAction(task -> check.add(times));
            }

            TaskDispatcher<String> taskDispatcher = builder.build();
            taskDispatcher.give("kerker");

            List<Object> expect = IntStream.range(0, actionCount).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            AssertionsEx.assertThat(check).isEqualTo(expect);
        });
    }

    @Test
    public void giveResult() {
        TaskExecuteResult[] results = TaskExecuteResult.values();

        TaskDispatcherBuilder<String> taskDispatcherBuilder = Mockito.spy(getBuilder());
        TaskExecutorBuilder taskExecutorBuilder = Mockito.spy(taskDispatcherBuilder.getTaskExecutorBuilder());
        TaskExecutor taskExecutor = Mockito.mock(TaskExecutor.class);

        AtomicInteger counter = new AtomicInteger(0);
        Mockito.doAnswer(invocation -> results[counter.getAndIncrement() % results.length]).when(taskExecutor).run(Mockito.any(), Mockito.any());
        Mockito.when(taskExecutorBuilder.build()).thenReturn(taskExecutor);
        Mockito.when(taskDispatcherBuilder.getTaskExecutorBuilder()).thenReturn(taskExecutorBuilder);

        int actionCount = results.length + 1;
        IntStream.range(0, actionCount).forEach(i -> taskDispatcherBuilder.giveAction(task -> {
        }));

        TaskDispatcher<String> taskDispatcher = taskDispatcherBuilder.build();
        TaskGiveResult<String> giveResult = taskDispatcher.give("kerker");

        AssertionsEx.assertThat(giveResult.getResultCount(TaskExecuteResult.EXECUTED)).isEqualTo(2);
        AssertionsEx.assertThat(giveResult.getResultCount(TaskExecuteResult.INTERRUPTED)).isEqualTo(1);
        AssertionsEx.assertThat(giveResult.getResultCount(TaskExecuteResult.CLOSED)).isEqualTo(1);
    }

    @Test
    public void waitAllTaskFinish_close___about_taskExecutor_series() throws Exception {
        TaskDispatcherBuilder<String> taskDispatcherBuilder = Mockito.spy(getBuilder());
        TaskExecutorBuilder taskExecutorBuilder = Mockito.spy(taskDispatcherBuilder.getTaskExecutorBuilder());
        TaskExecutor taskExecutor = Mockito.mock(TaskExecutor.class);

        Mockito.when(taskExecutorBuilder.build()).thenReturn(taskExecutor);
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
