package org.misty.utils.task.executor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.Tracked;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskParallelExecutorBuilderTest {

    @Test
    public void giveWaitRestUnitMs() {
        TaskParallelExecutorBuilder builder = new TaskParallelExecutorBuilder()
                .giveWaitRestUnitMs(10) // 下限
                .giveWaitRestUnitMs(1000) // 上限
                ;

        Assertions.assertThat(builder.getWaitRestUnitMs()).isEqualTo(1000);

        AssertionsEx.awareThrown(() -> builder.giveWaitRestUnitMs(9))
                .hasMessageContaining("waitRestUnitMs")
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> builder.giveWaitRestUnitMs(1001))
                .hasMessageContaining("waitRestUnitMs")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveExecutorService() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        TaskParallelExecutorBuilder builder = new TaskParallelExecutorBuilder()
                .giveExecutorService(executorService);

        Assertions.assertThat(builder.getExecutorService() == executorService).isTrue();
    }

    @Test
    public void build() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        long waitRestUnitMs = 100; // default

        TaskParallelExecutor executor = new TaskParallelExecutorBuilder()
                .giveTracked(Tracked.create())
                .giveExecutorService(executorService)
                .build();

        Assertions.assertThat(executor.getExecutorService() == executorService).isTrue();
        Assertions.assertThat(executor.getWaitRestUnitMs()).isEqualTo(waitRestUnitMs);
    }

}
