package org.misty.utils.lock;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class WakeupSingleLockTest extends WakeupLockTest {

    private final int testLoopTimes = 10;

    private final long timeoutMax = 60;

    private final long timeoutMin = timeoutMax / 2;

    @Test
    public void waiting_for_every() {
        super.waiting_for_every(this.testLoopTimes, WakeupLock::ofSingleLock);
    }

    @Test
    public void waiting_for_every_interruptHandler() {
        super.waiting_for_every_interruptHandler(this.testLoopTimes, WakeupLock::ofSingleLock);
    }

    @Test
    public void waiting_for_timeout() {
        super.waiting_for_timeout(this.testLoopTimes, WakeupLock::ofSingleLock);
    }

    @Test
    public void waiting_for_timeout_interruptHandler() {
        super.waiting_for_timeout_interruptHandler(this.testLoopTimes, WakeupLock::ofSingleLock);
    }

    @Test
    public void waiting_for_every_aware_action_in_synchronized() {
        { // 因為先執行wakeup了, 所以waiting執行的時候已被喚醒所以不會等待, 所以不會有2跟3
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.wakeup(() -> {
                    check.add("1");
                    restAction.run();
                });
            }, check -> { // second execute in different thread
                lock.waiting(() -> { // before wait in synchronized
                    check.add("2");
                }, () -> { // after wait in synchronized
                    check.add("3");
                });
            }, "1");
        }

        { // 因為先執行了waiting, 且在waiting之前等待wakeup喚醒, 所以順序是132
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.waiting(() -> { // before wait in synchronized
                    check.add("1");
                    restAction.run();
                }, () -> { // after wait in synchronized
                    check.add("2");
//                    restAction.run(); // 在這等待或上面等待結果一樣, 因為都是在synchronized內等待
                });
            }, check -> { // second execute in different thread
                lock.wakeup(() -> {
                    check.add("3");
                });
            }, "1", "3", "2");
        }

        { // 同上, 只是測試 waitingAndAwareBefore
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.waitingAndAwareBefore(() -> { // before wait in synchronized
                    check.add("1");
                    restAction.run();
                });
            }, check -> { // second execute in different thread
                lock.wakeup(() -> {
                    check.add("2");
                });
            }, "1", "2");
        }

        { // 同上上, 只是測試 waitingAndAwareAfter
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.waitingAndAwareAfter(() -> { // before wait in synchronized
                    check.add("1");
                    restAction.run();
                });
            }, check -> { // second execute in different thread
                lock.wakeup(() -> {
                    check.add("2");
                });
            }, "2", "1");
        }
    }

    @Test
    public void waiting_for_timeout_aware_action_in_synchronized() {
        { // 因為先執行wakeup了, 所以waiting執行的時候已被喚醒所以不會等待, 所以不會有2跟3
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.wakeup(() -> {
                    check.add("1");
                    restAction.run();
                });
            }, check -> { // second execute in different thread
                lock.waiting(this.timeoutMin, () -> { // before wait in synchronized
                    check.add("2");
                }, () -> { // after wait in synchronized
                    check.add("3");
                });
            }, "1");
        }

        { // 因為先執行了waiting, 且waiting timeout小於timeoutMin, 所以waiting前後都會比wakeup早執行, 所以順序是123
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.waiting(this.timeoutMin / 2, () -> { // before wait in synchronized
                    check.add("1");
                }, () -> { // after wait in synchronized
                    check.add("2");
                });
            }, check -> { // second execute in different thread
                lock.wakeup(() -> {
                    check.add("3");
                });
            }, "1", "2", "3");
        }

        { // 同上, 只是測試 waitingAndAwareBefore
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.waitingAndAwareBefore(this.timeoutMin / 2, () -> { // before wait in synchronized
                    check.add("1");
                });
            }, check -> { // second execute in different thread
                lock.wakeup(() -> {
                    check.add("2");
                });
            }, "1", "2");
        }

        { // 同上上, 只是測試 waitingAndAwareAfter
            WakeupSingleLock lock = WakeupLock.ofSingleLock();
            super.aware_action_in_synchronized(this.timeoutMax, this.timeoutMin, (check, restAction) -> { // first execute in different thread
                lock.waitingAndAwareAfter(this.timeoutMin / 2, () -> { // before wait in synchronized
                    check.add("1");
                });
            }, check -> { // second execute in different thread
                lock.wakeup(() -> {
                    check.add("2");
                });
            }, "1", "2");
        }
    }

    @Test
    public void cant_reuse() {
        WakeupSingleLock lock = WakeupLock.ofSingleLock();
        lock.wakeup();

        AssertionsEx.awareThrown(lock::wakeup).isInstanceOf(IllegalStateException.class);
    }

}
