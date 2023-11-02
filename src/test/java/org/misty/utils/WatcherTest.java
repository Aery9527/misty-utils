package org.misty.utils;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

class WatcherTest {

    private final Percentage deviation = Percentage.withPercentage(5);

    @Test
    public void create() {
        Watcher watcher = Watcher.create();
        long currentTimeMillis = System.currentTimeMillis();
        long watcherStartTime = watcher.getStartTime();
        Assertions.assertThat(currentTimeMillis).isCloseTo(watcherStartTime, this.deviation);
    }

    @Test
    public void through() throws InterruptedException {
        long resetTime = 300;

        Watcher watcher = Watcher.create();
        Thread.sleep(resetTime);
        long through = watcher.through();

        Assertions.assertThat(through).isCloseTo(resetTime, this.deviation);
    }

    @Test
    public void over() throws InterruptedException {
        long resetTime = 300;
        long buffer = (long) (resetTime * deviation.value / 100);

        Watcher watcher = Watcher.create();
        Thread.sleep(resetTime);

        boolean check1 = watcher.over(resetTime - buffer);
        boolean check2 = watcher.over(resetTime + buffer);

        Assertions.assertThat(check1).isTrue();
        Assertions.assertThat(check2).isFalse();
    }

    @Test
    public void reset() throws InterruptedException {
        long resetTime = 300;

        Watcher watcher = Watcher.create();
        Thread.sleep(resetTime);

        watcher.reset();

        long currentTimeMillis = System.currentTimeMillis();
        long watcherStartTime = watcher.getStartTime();
        Assertions.assertThat(currentTimeMillis).isCloseTo(watcherStartTime, this.deviation);
    }

}
