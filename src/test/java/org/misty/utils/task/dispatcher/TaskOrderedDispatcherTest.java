package org.misty.utils.task.dispatcher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.ex.CountDownLatchEx;
import org.misty.utils.ex.ThreadEx;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.executor.TaskExecutor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class TaskOrderedDispatcherTest extends TaskBaseDispatcherTest {

    private class SequentialityChecker implements ConsumerEx<Integer> {

        private final AtomicInteger threadCounter = new AtomicInteger(0);

        private final AtomicInteger timesCounter = new AtomicInteger(0);

        private volatile boolean fail = false;

        @Override
        public void handle(Integer arg) throws Exception {
            int count = this.threadCounter.incrementAndGet();

            if (count > 1) {
                this.fail = true;
            }

            ThreadEx.restRandom(5, 10);

            this.threadCounter.decrementAndGet();

            this.timesCounter.incrementAndGet();
        }

        public boolean isFail() {
            return fail;
        }

        public int times() {
            return timesCounter.get();
        }
    }

    @Override
    public <Task> TaskDispatcherBuilder<Task> builderSetting(TaskDispatcherBuilder<Task> builder) {
        return builder.withOrdered();
    }

    /**
     * 檢查是否每個action同一時間只有一個thread會執行
     */
    @Test
    public void sequentiality() {
        int times = 600;
        SequentialityChecker check1 = new SequentialityChecker();
        SequentialityChecker check2 = new SequentialityChecker();
        SequentialityChecker check3 = new SequentialityChecker();

        TaskDispatcherBuilder<Integer> builder = builderSetting(TaskDispatcher.builder());
        TaskDispatcher<Integer> taskDispatcher = builder
                .giveAction(check1)
                .giveAction(number -> number % 2 == 0, check2)
                .giveAction(number -> number % 3 == 0, check3)
                .build();

        CountDownLatchEx latch = new CountDownLatchEx(times);

        TaskExecutor taskExecutor = TaskExecutor.builder().withParallel().build();
        IntStream.rangeClosed(1, times).forEach(number -> {
            taskExecutor.run(() -> {
                taskDispatcher.give(number);
                latch.countDown();
            });
        });

        latch.wating();
        taskDispatcher.waitAllTaskFinish();

        Assertions.assertThat(check1.isFail()).isFalse();
        Assertions.assertThat(check2.isFail()).isFalse();
        Assertions.assertThat(check3.isFail()).isFalse();

        Assertions.assertThat(check1.times()).isEqualTo(times);
        Assertions.assertThat(check2.times()).isEqualTo(times / 2);
        Assertions.assertThat(check3.times()).isEqualTo(times / 3);
    }

}
