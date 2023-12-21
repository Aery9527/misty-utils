package org.misty.utils;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty.utils.ex.ThreadEx;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.stream.IntStream;

class ExecutorSwitchTest {

    @Test
    public void create() {
        {
            String name = "kerker";
            ExecutorSwitch executorSwitch = ExecutorSwitch.create(name);
            Tracked tracked = executorSwitch.getTracked();
            Assertions.assertThat(tracked.getId().startsWith(name)).isTrue();
        }

        {
            Tracked tracked = Tracked.create();
            ExecutorSwitch executorSwitch = ExecutorSwitch.create(tracked);
            Assertions.assertThat(executorSwitch.getTracked()).isEqualTo(tracked);
        }
    }

    @Test
    public void create_errorHandler() {
        AtomicInteger counter1 = new AtomicInteger(0);
        BiPredicate<Integer, Exception> defaultErrorHandler = (times, error) -> {
            counter1.incrementAndGet();
            return ExecutorSwitch.CONTINUE_WHEN_ERROR;
        };

        AtomicInteger counter2 = new AtomicInteger(0);
        BiPredicate<Integer, Exception> currentErrorHandler = (times, error) -> {
            counter2.incrementAndGet();
            return ExecutorSwitch.CONTINUE_WHEN_ERROR;
        };

        ExecutorSwitch executorSwitch1 = ExecutorSwitch.create("kerker", defaultErrorHandler);
        ExecutorSwitch executorSwitch2 = ExecutorSwitch.create(Tracked.create(), defaultErrorHandler);

        executorSwitch1.run(times -> {
            throw new RuntimeException();
        });
        Assertions.assertThat(counter1.get()).isEqualTo(1);
        executorSwitch2.run(times -> {
            throw new RuntimeException();
        });
        Assertions.assertThat(counter1.get()).isEqualTo(2);

        executorSwitch1.run(times -> {
            throw new RuntimeException();
        }, currentErrorHandler);
        Assertions.assertThat(counter2.get()).isEqualTo(1);
        executorSwitch2.run(times -> {
            throw new RuntimeException();
        }, currentErrorHandler);
        Assertions.assertThat(counter2.get()).isEqualTo(2);
    }

    @Test
    public void run_withSerial() {
        ExecutorSwitch executorSwitch = ExecutorSwitch.create(); // default is withSerial

        AtomicReference<Thread> threadRef = new AtomicReference<>();
        executorSwitch.run(times -> threadRef.set(Thread.currentThread()));

        Assertions.assertThat(threadRef.get()).isEqualTo(Thread.currentThread());
    }

    @Test
    public void run_withParallel() {
        int threadNumber = 5;
        ExecutorSwitch executorSwitch = ExecutorSwitch.create().withParallel(threadNumber);

        List<Thread> threads = new ArrayList<>(threadNumber);
        IntStream.range(0, threadNumber).forEach(index -> {
            executorSwitch.run(times -> threads.add(Thread.currentThread()));
        });

        executorSwitch.waitFinish(); // 這裡要等待fork出去的thread都執行完再往下

        Assertions.assertThat(threads).doesNotContain(Thread.currentThread());
    }

    @Test
    public void run_error_do_continue_or_break_withSerial() {
        int stopTimes = 3;

        AtomicInteger errorCounter = new AtomicInteger(0);
        ExecutorSwitch executorSwitch = ExecutorSwitch.create((times, e) -> { // defaultErrorHandler
            int count = errorCounter.incrementAndGet();
            return count == stopTimes ? ExecutorSwitch.BREAK_WHEN_ERROR : ExecutorSwitch.CONTINUE_WHEN_ERROR;
        }).withSerial();

        BooleanSupplier test = () -> executorSwitch.run(times -> {
            throw new RuntimeException();
        });

        Assertions.assertThat(test.getAsBoolean()).isTrue();
        Assertions.assertThat(test.getAsBoolean()).isTrue();
        Assertions.assertThat(test.getAsBoolean()).isTrue();
        Assertions.assertThat(test.getAsBoolean()).isFalse();
        Assertions.assertThat(test.getAsBoolean()).isFalse();
        Assertions.assertThat(test.getAsBoolean()).isFalse();
    }

    @Test
    public void run_error_do_continue_or_break_withParallel() {
        int stopTimes = 3;
        int threadNumber = 5;

        AtomicInteger errorCounter = new AtomicInteger(0);
        ExecutorSwitch executorSwitch = ExecutorSwitch.create((times, e) -> { // defaultErrorHandler
            int count = errorCounter.incrementAndGet();
            return count >= stopTimes ? ExecutorSwitch.BREAK_WHEN_ERROR : ExecutorSwitch.CONTINUE_WHEN_ERROR;
        }).withParallel(threadNumber);

        Map<Boolean, AtomicInteger> resultCounter = new HashMap<>();
        for (int i = 0; i < threadNumber; i++) {
            boolean executed = executorSwitch.run(times -> {
                throw new RuntimeException();
            });
            resultCounter.computeIfAbsent(executed, key -> new AtomicInteger(0)).incrementAndGet();
            ThreadEx.rest(100);
        }

        executorSwitch.waitFinish(); // 這裡要等待fork出去的thread都執行完再往下

        Assertions.assertThat(errorCounter.get()).isEqualTo(stopTimes);
        Assertions.assertThat(resultCounter.get(Boolean.TRUE).get()).isEqualTo(stopTimes);
        Assertions.assertThat(resultCounter.get(Boolean.FALSE).get()).isEqualTo(threadNumber - stopTimes);
    }

    @Test
    public void startLock_and_reset() {
        ExecutorSwitch executorSwitch = ExecutorSwitch.create();
        executorSwitch.run(times -> ThreadEx.rest(10));

        AssertionsEx.assertThrown(executorSwitch::withSerial).isInstanceOf(IllegalStateException.class);
        AssertionsEx.assertThrown(() -> executorSwitch.withParallel(1)).isInstanceOf(IllegalStateException.class);

        executorSwitch.reset();

        executorSwitch.withParallel();
        executorSwitch.withSerial();
    }

    @Test
    public void waitFinish() {
        long restLeast = 50;
        long restMost = 100;
        long restLeast_2 = restLeast / 2;

        Consumer<ExecutorSwitch> runRest = executorSwitch -> executorSwitch.run(times -> ThreadEx.restRandom(restLeast, restMost));

        Consumer<ExecutorSwitch> test = executorSwitch -> {
            boolean isWithSerial = executorSwitch.isWithSerial();

            Watcher watcher = Watcher.create();
            runRest.accept(executorSwitch);
            runRest.accept(executorSwitch);
            runRest.accept(executorSwitch);
            long through1 = watcher.through();

            if (isWithSerial) {
                Assertions.assertThat(through1).isGreaterThan(restLeast * 3); // 單緒是先完成任務才會往後跑
            } else {
                Assertions.assertThat(through1).isLessThanOrEqualTo(10); // 多緒這邊不會花太多時間
            }

            watcher.reset();
            boolean finished1 = executorSwitch.waitFinish(Duration.ofMillis(restLeast_2));
            long through2 = watcher.through();

            if (isWithSerial) {
                Assertions.assertThat(finished1).isTrue(); // 單緒是先完成任務才會往後跑
                Assertions.assertThat(through2).isLessThanOrEqualTo(10); // 單緒這邊並不會有等待時間
            } else {
                Assertions.assertThat(finished1).isFalse(); // 多緒任務還在執行未完成
                Assertions.assertThat(through2).isCloseTo(restLeast_2, Percentage.withPercentage(100)); // 所以多緒會有等待時間
            }

            watcher.reset();
            boolean finished2 = executorSwitch.waitFinish(Duration.ofMillis(restMost));
            long through3 = watcher.through();

            Assertions.assertThat(finished2).isTrue();
            if (isWithSerial) {
                Assertions.assertThat(through3).isLessThanOrEqualTo(10); // 單緒這邊並不會有等待時間
            } else {
                Assertions.assertThat(through3).isGreaterThan(restLeast - (restLeast_2 + 20)); // 多緒前面等了restLeast_2, 所以這邊會比restLeast少
                Assertions.assertThat(through3).isLessThan(restMost - restLeast_2 + 20); // +20是因為waitFinish的實作有可能會多等一點點
            }
        };

        for (int i = 0; i < 10; i++) {
            test.accept(ExecutorSwitch.create().withSerial());
            test.accept(ExecutorSwitch.create().withParallel());
        }
    }

}
