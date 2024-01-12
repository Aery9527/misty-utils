package org.misty.utils.lock;

import org.misty.utils.Tracked;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface WakeupLock {

    static WakeupSingleLock ofSingleLock() {
        return new WakeupSingleLock(Tracked.create());
    }

    static WakeupSingleLock ofSingleLock(String name) {
        return new WakeupSingleLock(Tracked.create(name));
    }

    static WakeupSingleLock ofSingleLock(Tracked tracked) {
        return new WakeupSingleLock(tracked);
    }

    static WakeupReuseLock ofReuseLock() {
        return new WakeupReuseLock(Tracked.create());
    }

    static WakeupReuseLock ofReuseLock(String name) {
        return new WakeupReuseLock(Tracked.create(name));
    }

    static WakeupReuseLock ofReuseLock(Tracked tracked) {
        return new WakeupReuseLock(tracked);
    }

    default void wakeup() {
        wakeup(() -> {
        });
    }

    default void wakeup(Runnable actionInSynchronized) {
        wakeup(() -> {
            actionInSynchronized.run();
            return null;
        });
    }

    <ReturnType> ReturnType wakeup(Supplier<ReturnType> actionInSynchronized);

    default boolean waiting() {
        return waiting(e -> {
        });
    }

    default boolean waitingAndAwareBefore(Runnable actionBeforeWaitSynchronized) {
        return waiting(actionBeforeWaitSynchronized, () -> {
        });
    }

    default boolean waitingAndAwareAfter(Runnable actionAfterWaitSynchronized) {
        return waiting(() -> {
        }, actionAfterWaitSynchronized);
    }

    default boolean waiting(Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized) {
        return waiting(e -> {
        }, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
    }

    default boolean waiting(Consumer<InterruptedException> interruptHandler) {
        return waiting(interruptHandler, () -> {
        }, () -> {
        });
    }

    default boolean waitingAndAwareBefore(Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized) {
        return waiting(interruptHandler, actionBeforeWaitSynchronized, () -> {
        });
    }

    default boolean waitingAndAwareAfter(Consumer<InterruptedException> interruptHandler, Runnable actionAfterWaitSynchronized) {
        return waiting(interruptHandler, () -> {
        }, actionAfterWaitSynchronized);
    }

    boolean waiting(Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized);

    default boolean waiting(long timeout) {
        return waiting(timeout, e -> {
        });
    }

    default boolean waitingAndAwareBefore(long timeout, Runnable actionBeforeWaitSynchronized) {
        return waiting(timeout, actionBeforeWaitSynchronized, () -> {
        });
    }

    default boolean waitingAndAwareAfter(long timeout, Runnable actionAfterWaitSynchronized) {
        return waiting(timeout, () -> {
        }, actionAfterWaitSynchronized);
    }

    default boolean waiting(long timeout, Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized) {
        return waiting(timeout, e -> {
        }, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
    }

    default boolean waiting(long timeout, Consumer<InterruptedException> interruptHandler) {
        return waiting(timeout, interruptHandler, () -> {
        }, () -> {
        });
    }

    default boolean waitingAndAwareBefore(long timeout, Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized) {
        return waiting(timeout, interruptHandler, actionBeforeWaitSynchronized, () -> {
        });
    }

    default boolean waitingAndAwareAfter(long timeout, Consumer<InterruptedException> interruptHandler, Runnable actionAfterWaitSynchronized) {
        return waiting(timeout, interruptHandler, () -> {
        }, actionAfterWaitSynchronized);
    }

    boolean waiting(long timeout, Consumer<InterruptedException> interruptHandler,
                    Runnable actionBeforeWaitSynchronized,
                    Runnable actionAfterWaitSynchronized);

}
