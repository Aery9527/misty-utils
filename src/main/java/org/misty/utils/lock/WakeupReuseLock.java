package org.misty.utils.lock;

import org.misty.utils.Tracked;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 可重用喚醒鎖, 主要是可以任意的調用{@link #wakeup}系列的方法來喚醒正在等待的thread,
 * 缺點是如果使用邏輯是只有一次調用{@link #wakeup}, 那麼這之後調用{@link #waiting}系列的方法不再可能被喚醒,
 * 有timeout的就會等到timeout, 沒有timeout的就會永遠等下去.
 * <p>
 * 所以如果需要{@link #wakeup}一次之後, 後續的{@link #waiting}不會等待直接被喚醒的情境,
 * 請使用{@link WakeupSingleLock}.
 */
public class WakeupReuseLock extends WakeupBaseLock {

    private final Object lock = new Object();

    public WakeupReuseLock(Tracked tracked) {
        super(tracked);
    }

    @Override
    public <ReturnType> ReturnType wakeup(Supplier<ReturnType> actionInSynchronized) {
        synchronized (this.lock) {
            this.lock.notifyAll();

            return actionInSynchronized.get();
        }
    }

    @Override
    public boolean waiting(Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized) {
        try {
            synchronized (this.lock) {
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
        try {
            synchronized (this.lock) {
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
