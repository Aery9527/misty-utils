package org.misty.utils.lock;

import org.misty.utils.Tracked;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 單次喚醒鎖, 主要是{@link #wakeup}只能操作一次, 之後的{@link #waiting}系列的方法會直接喚醒不會進入等待,
 * 適用於併發狀況下, 只有一次{@link #wakeup}操作但{@link #waiting}有可能在這之後被調用的情境.
 */
public class WakeupSingleLock extends WakeupBaseLock {

    private final Object lock = new Object();

    private volatile boolean isWakeup = false;

    public WakeupSingleLock(Tracked tracked) {
        super(tracked);
    }

    @Override
    public <ReturnType> ReturnType wakeup(Supplier<ReturnType> actionInSynchronized) {
        Supplier<IllegalStateException> exceptionSupplier = () -> {
            String msg = getClass().getSimpleName() + ":" + super.getTracked().say("already wakeup");
            return new IllegalStateException(msg);
        };

        if (this.isWakeup) {
            throw exceptionSupplier.get();

        } else {
            synchronized (this.lock) {
                if (this.isWakeup) {
                    throw exceptionSupplier.get();
                }
                this.isWakeup = true;

                this.lock.notifyAll();

                return actionInSynchronized.get();
            }
        }
    }

    @Override
    public boolean waiting(Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized) {
        if (this.isWakeup) {
            return false;
        }

        try {
            synchronized (this.lock) {
                if (this.isWakeup) {
                    return false;
                }

                actionBeforeWaitSynchronized.run();
                this.lock.wait();
                actionAfterWaitSynchronized.run();
            }
            return false;

        } catch (InterruptedException e) {
            interruptHandler.accept(e);
            return true;
        }
    }

    @Override
    public boolean waiting(long timeout, Consumer<InterruptedException> interruptHandler,
                           Runnable actionBeforeWaitSynchronized,
                           Runnable actionAfterWaitSynchronized) {
        if (this.isWakeup) {
            return false;
        }

        try {
            synchronized (this.lock) {
                if (this.isWakeup) {
                    return false;
                }

                actionBeforeWaitSynchronized.run();
                this.lock.wait(timeout);
                actionAfterWaitSynchronized.run();
            }
            return false;
        } catch (InterruptedException e) {
            interruptHandler.accept(e);
            return true;
        }
    }

}
