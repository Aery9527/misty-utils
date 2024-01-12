package org.misty.utils.lock;

import org.misty._utils.AssertionsEx;
import org.misty.utils.Watcher;
import org.misty.utils.ex.CountDownLatchEx;
import org.misty.utils.ex.ThreadEx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WakeupLockTest {

    public void waiting_for_every(int testLoopTimes, Supplier<WakeupLock> lockSupplier) {
        for (int times = 0; times < testLoopTimes; times++) {
            WakeupLock lock = lockSupplier.get();

            long timeout = 60;
            CountDownLatchEx latch1 = new CountDownLatchEx(1);
            CountDownLatchEx latch2 = new CountDownLatchEx(2);

            AtomicReference<Boolean> interruptFlag = new AtomicReference<>();

            AtomicLong waitingTime = new AtomicLong(0);
            ThreadEx.fork("waiting" + times, () -> {
                latch1.wating();

                Watcher watcher = Watcher.create();
                boolean interrupt = lock.waiting();
                waitingTime.set(watcher.through());

                interruptFlag.set(interrupt);

                latch2.countDown();
            });

            ThreadEx.fork("wakeup" + times, () -> {
                latch1.wating();

                ThreadEx.rest(timeout);
                lock.wakeup();

                latch2.countDown();
            });

            latch1.countDown();
            latch2.wating();

            AssertionsEx.assertThat(waitingTime.get()).isCloseTo(timeout, AssertionsEx.within(20L));
            AssertionsEx.assertThat(interruptFlag.get()).isFalse();
        }
    }

    public void waiting_for_every_interruptHandler(int testLoopTimes, Supplier<WakeupLock> lockSupplier) {
        for (int times = 0; times < testLoopTimes; times++) {
            WakeupLock lock = lockSupplier.get();

            long timeout = 60;
            CountDownLatchEx latch1 = new CountDownLatchEx(1);
            CountDownLatchEx latch2 = new CountDownLatchEx(1);
            CountDownLatchEx latch3 = new CountDownLatchEx(1);

            AtomicReference<Thread> waitingThread = new AtomicReference<>();
            AtomicReference<Boolean> interruptFlag = new AtomicReference<>();

            AtomicLong waitingTime = new AtomicLong(0);
            ThreadEx.fork(() -> {
                latch1.wating();
                waitingThread.set(Thread.currentThread());
                latch2.countDown();

                Watcher watcher = Watcher.create();
                boolean interrupt = lock.waiting();
                waitingTime.set(watcher.through());

                interruptFlag.set(interrupt);

                latch3.countDown();
            });

            latch1.countDown();
            latch2.wating();

            ThreadEx.rest(timeout);
            waitingThread.get().interrupt();

            latch3.wating();

            AssertionsEx.assertThat(waitingTime.get()).isCloseTo(timeout, AssertionsEx.within(20L));
            AssertionsEx.assertThat(interruptFlag.get()).isTrue();
        }
    }

    public void waiting_for_timeout(int testLoopTimes, Supplier<WakeupLock> lockSupplier) {
        for (int times = 0; times < testLoopTimes; times++) {
            WakeupLock lock = lockSupplier.get();

            boolean wakeupOverTimeout = times % 2 == 0;
            long timeout = 60;
            long buffer = (wakeupOverTimeout ? 1 : -1) * timeout / 2;
            CountDownLatchEx latch1 = new CountDownLatchEx(1);
            CountDownLatchEx latch2 = new CountDownLatchEx(2);

            AtomicReference<Boolean> interruptFlag = new AtomicReference<>();

            AtomicLong waitingTime = new AtomicLong(0);
            ThreadEx.fork("waiting" + times, () -> {
                latch1.wating();

                Watcher watcher = Watcher.create();
                boolean interrupt = lock.waiting(timeout);
                waitingTime.set(watcher.through());

                interruptFlag.set(interrupt);

                latch2.countDown();
            });

            ThreadEx.fork("wakeup" + times, () -> {
                latch1.wating();

                ThreadEx.rest(timeout + buffer);
                lock.wakeup();

                latch2.countDown();
            });

            latch1.countDown();
            latch2.wating();

            long expected = wakeupOverTimeout ? timeout : timeout + buffer;
            AssertionsEx.assertThat(waitingTime.get()).isCloseTo(expected, AssertionsEx.within(20L));
            AssertionsEx.assertThat(interruptFlag.get()).isFalse();
        }
    }

    public void waiting_for_timeout_interruptHandler(int testLoopTimes, Supplier<WakeupLock> lockSupplier) {
        for (int times = 0; times < testLoopTimes; times++) {
            WakeupLock lock = lockSupplier.get();

            boolean interruptOverTimeout = times % 2 == 0;
            long timeout = 60;
            long buffer = (interruptOverTimeout ? 1 : -1) * timeout / 2;
            CountDownLatchEx latch1 = new CountDownLatchEx(1);
            CountDownLatchEx latch2 = new CountDownLatchEx(1);
            CountDownLatchEx latch3 = new CountDownLatchEx(1);

            AtomicReference<Thread> waitingThread = new AtomicReference<>();
            AtomicReference<Boolean> interruptFlag = new AtomicReference<>();
            AtomicBoolean interruptHandlerFlag = new AtomicBoolean(false);

            AtomicLong waitingTime = new AtomicLong(0);
            ThreadEx.fork(() -> {
                latch1.wating();
                waitingThread.set(Thread.currentThread());
                latch2.countDown();

                Watcher watcher = Watcher.create();
                boolean interrupt = lock.waiting(timeout, e -> interruptHandlerFlag.set(true));
                waitingTime.set(watcher.through());

                interruptFlag.set(interrupt);

                latch3.countDown();
            });

            latch1.countDown();
            latch2.wating();

            ThreadEx.rest(timeout + buffer);
            waitingThread.get().interrupt();

            latch3.wating();

            long expected = interruptOverTimeout ? timeout : timeout + buffer;
            AssertionsEx.assertThat(waitingTime.get()).isCloseTo(expected, AssertionsEx.within(20L));
            AssertionsEx.assertThat(interruptFlag.get()).isEqualTo(!interruptOverTimeout);
            AssertionsEx.assertThat(interruptHandlerFlag.get()).isEqualTo(!interruptOverTimeout);
        }
    }

    public void aware_action_in_synchronized(
            long timeoutMax,
            long timeoutMin,
            BiConsumer<List<String>, Runnable> firstExecute,
            Consumer<List<String>> secondExecute,
            String... expectedOrderList
    ) {
        CountDownLatchEx latch1 = new CountDownLatchEx(1);
        CountDownLatchEx latch2 = new CountDownLatchEx(2);

        List<String> check = new ArrayList<>();

        ThreadEx.fork(() -> {
            latch1.wating();

            firstExecute.accept(check, () -> ThreadEx.rest(timeoutMax)); // 等超過下面thread的timeout, 讓下面thread卡在synchronized外

            latch2.countDown();
        });

        ThreadEx.fork(() -> {
            latch1.wating();

            ThreadEx.rest(timeoutMin); // 先讓上面的thread進去synchronized
            secondExecute.accept(check);

            latch2.countDown();
        });

        ThreadEx.rest(timeoutMin);
        latch1.countDown();
        latch2.wating();

        AssertionsEx.assertThat(check).containsExactly(expectedOrderList);
    }

}
