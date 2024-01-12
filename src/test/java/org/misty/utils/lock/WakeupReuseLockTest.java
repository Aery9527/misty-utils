package org.misty.utils.lock;

import org.junit.jupiter.api.Test;

public class WakeupReuseLockTest extends WakeupLockTest {

    private final int testLoopTimes = 10;

    private final long timeoutMax = 60;

    private final long timeoutMin = timeoutMax / 2;

    @Test
    public void waiting_for_every() {
        super.waiting_for_every(this.testLoopTimes, WakeupLock::ofReuseLock);
    }

    @Test
    public void waiting_for_every_interruptHandler() {
        super.waiting_for_every_interruptHandler(this.testLoopTimes, WakeupLock::ofReuseLock);
    }

    @Test
    public void waiting_for_timeout() {
        super.waiting_for_timeout(this.testLoopTimes, WakeupLock::ofReuseLock);
    }

    @Test
    public void waiting_for_timeout_interruptHandler() {
        super.waiting_for_timeout_interruptHandler(this.testLoopTimes, WakeupLock::ofReuseLock);
    }

    @Test
    public void waiting_for_every_aware_action_in_synchronized() {
        // 無法測試先wakeup再waiting的情境, 因為 WakeupReuseLock 不像 WakeupSingleLock 單次喚醒可以先操作wakeup再操作waiting而不會永久wait

        { // 因為先執行了waiting, 且在waiting之前等待wakeup喚醒, 所以順序是132
            WakeupReuseLock lock = WakeupLock.ofReuseLock();
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
            WakeupReuseLock lock = WakeupLock.ofReuseLock();
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
            WakeupReuseLock lock = WakeupLock.ofReuseLock();
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
        { // 雖然先執行wakeup在waiting不會被喚醒, 但因為有timeout所以還是會醒來往下執行, 所以順序是123
            WakeupReuseLock lock = WakeupLock.ofReuseLock();
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
            }, "1", "2", "3");
        }

        { // 因為先執行了waiting, 且waiting timeout小於timeoutMin, 所以waiting前後都會比wakeup早執行, 所以順序是123
            WakeupReuseLock lock = WakeupLock.ofReuseLock();
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
            WakeupReuseLock lock = WakeupLock.ofReuseLock();
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
            WakeupReuseLock lock = WakeupLock.ofReuseLock();
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
    public void can_reuse() {
        WakeupReuseLock lock = WakeupLock.ofReuseLock();
        lock.wakeup();
        lock.wakeup();
    }

}
