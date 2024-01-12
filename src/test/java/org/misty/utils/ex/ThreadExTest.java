package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadExTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void rest_normal() {
        long restMs = 300;
        long restMs_4 = restMs / 4;

        AtomicBoolean isInterrupt = new AtomicBoolean(false);
        Thread thread = new Thread(() -> isInterrupt.set(ThreadEx.rest(restMs)));

        printState("A", thread);

        ThreadEx.rest(restMs + restMs_4);

        printState("B", thread);

        Assertions.assertThat(isInterrupt).isFalse();
    }

    @Test
    public void rest_interrupt() {
        long restMs = 300;
        long restMs_4 = restMs / 4;

        AtomicBoolean isInterrupt = new AtomicBoolean(false);
        Thread thread = new Thread(() -> isInterrupt.set(ThreadEx.rest(restMs)));

        printState("A", thread);

        thread.start();
        ThreadEx.rest(restMs_4);

        printState("B", thread);

        thread.interrupt();
        ThreadEx.rest(restMs_4);

        printState("C", thread);

        Assertions.assertThat(isInterrupt).isTrue();
    }

    @Test
    public void restRandom() {
        long least = 5;
        long most = 10;
        long most_deviation = most + 5; // +5是為了避免誤差

//        for (int i = 0; i < 100; i++) {
//            Watcher watcher = Watcher.create();
//            ThreadEx.restRandom(least, most);
//            long through = watcher.through();
//
//            Assertions.assertThat(through).isGreaterThanOrEqualTo(least);
//            Assertions.assertThat(through).isLessThan(most_deviation);
//        }

        AssertionsEx.awareThrown(() -> ThreadEx.restRandom(0, -1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> ThreadEx.restRandom(-1, 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> ThreadEx.restRandom(10, 5)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void fork() {
        AtomicReference<Thread> threadRef1 = new AtomicReference<>();
        AtomicReference<Thread> threadRef2 = new AtomicReference<>();

        CountDownLatchEx latch = new CountDownLatchEx(2);

        ThreadEx.fork("fork", () -> {
            threadRef1.set(Thread.currentThread());
            latch.countDown();
        });
        ThreadEx.fork(() -> {
            threadRef2.set(Thread.currentThread());
            latch.countDown();
        });

        latch.wating();

        Assertions.assertThat(threadRef1.get()).isNotEqualTo(Thread.currentThread());
        Assertions.assertThat(threadRef2.get()).isNotEqualTo(Thread.currentThread());
        Assertions.assertThat(threadRef2.get()).isNotEqualTo(threadRef1.get());
    }

    private void printState(String point, Thread thread) {
        this.logger.info("[{}]", point);
        this.logger.info("alive       {}", thread.isAlive());
        this.logger.info("state       {}", thread.getState());
        this.logger.info("interrupted {}", thread.isInterrupted());
    }

}
