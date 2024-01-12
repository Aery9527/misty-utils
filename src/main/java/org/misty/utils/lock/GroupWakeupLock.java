package org.misty.utils.lock;

import org.misty.utils.Tracked;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GroupWakeupLock {

    private static Function<String, WakeupLock> genLockFactory(Tracked tracked, boolean useSingleLock) {
        return useSingleLock ? key -> WakeupLock.ofSingleLock(tracked) : key -> WakeupLock.ofReuseLock(tracked);
    }

    private final Map<String, WakeupLock> waitingLockMap = new ConcurrentHashMap<>();

    private final Tracked tracked;

    private final Function<String, WakeupLock> defaultLockFactory;

    private final Consumer<String> duplicatedKeyError;

    public GroupWakeupLock() {
        this(Tracked.create(GroupWakeupLock.class), true);
    }

    public GroupWakeupLock(Tracked tracked) {
        this(tracked, true);
    }

    public GroupWakeupLock(boolean useSingleLock) {
        this(Tracked.create(GroupWakeupLock.class), useSingleLock);
    }

    public GroupWakeupLock(Tracked tracked, boolean useSingleLock) {
        this(tracked, genLockFactory(tracked, useSingleLock));
    }

    public GroupWakeupLock(Tracked tracked, Function<String, WakeupLock> lockFactory) {
        this.tracked = tracked;
        this.defaultLockFactory = lockFactory;
        this.duplicatedKeyError = key -> {
            throw new IllegalStateException(this.tracked.say("key(%s) already exists", key));
        };
    }

    public GroupWakeupLock create(String key, boolean useSingleLock) {
        Tracked subTracked = tracked.link(key);
        create(key, genLockFactory(subTracked, useSingleLock), this.duplicatedKeyError);
        return this;
    }

    public GroupWakeupLock create(String key, Function<String, WakeupLock> lockFactory) {
        create(key, lockFactory, this.duplicatedKeyError);
        return this;
    }

    public void wakeup(String key) {
        create(key).wakeup();
    }

    public void wakeup(String key, Runnable actionInSynchronized) {
        create(key).wakeup(actionInSynchronized);
    }

    public <ReturnType> ReturnType wakeup(String key, Supplier<ReturnType> actionInSynchronized) {
        return create(key).wakeup(actionInSynchronized);
    }

    public void waiting(String key) {
        create(key).waiting();
    }

    public void waitingAndAwareBefore(String key, Runnable actionBeforeWaitSynchronized) {
        create(key).waitingAndAwareBefore(actionBeforeWaitSynchronized);
    }

    public void waitingAndAwareAfter(String key, Runnable actionAfterWaitSynchronized) {
        create(key).waitingAndAwareAfter(actionAfterWaitSynchronized);
    }

    public void waiting(String key, Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized) {
        create(key).waiting(actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
    }

    public void waiting(String key, Consumer<InterruptedException> interruptHandler) {
        create(key).waiting(interruptHandler);
    }

    public void waitingAndAwareBefore(String key, Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized) {
        create(key).waitingAndAwareBefore(interruptHandler, actionBeforeWaitSynchronized);
    }

    public void waitingAndAwareAfter(String key, Consumer<InterruptedException> interruptHandler, Runnable actionAfterWaitSynchronized) {
        create(key).waitingAndAwareAfter(interruptHandler, actionAfterWaitSynchronized);
    }

    public void waiting(String key, Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized) {
        create(key).waiting(interruptHandler, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
    }

    public void waiting(String key, long timeout) {
        create(key).waiting(timeout);
    }

    public void waitingAndAwareBefore(String key, long timeout, Runnable actionBeforeWaitSynchronized) {
        create(key).waitingAndAwareBefore(timeout, actionBeforeWaitSynchronized);
    }

    public void waitingAndAwareAfter(String key, long timeout, Runnable actionAfterWaitSynchronized) {
        create(key).waitingAndAwareAfter(timeout, actionAfterWaitSynchronized);
    }

    public void waiting(String key, long timeout, Runnable actionBeforeWaitSynchronized, Runnable actionAfterWaitSynchronized) {
        create(key).waiting(timeout, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
    }

    public void waiting(String key, long timeout, Consumer<InterruptedException> interruptHandler) {
        create(key).waiting(timeout, interruptHandler);
    }

    public void waitingAndAwareBefore(String key, long timeout, Consumer<InterruptedException> interruptHandler, Runnable actionBeforeWaitSynchronized) {
        create(key).waitingAndAwareBefore(timeout, interruptHandler, actionBeforeWaitSynchronized);
    }

    public void waitingAndAwareAfter(String key, long timeout, Consumer<InterruptedException> interruptHandler, Runnable actionAfterWaitSynchronized) {
        create(key).waitingAndAwareAfter(timeout, interruptHandler, actionAfterWaitSynchronized);
    }

    public void waiting(String key, long timeout, Consumer<InterruptedException> interruptHandler,
                        Runnable actionBeforeWaitSynchronized,
                        Runnable actionAfterWaitSynchronized) {
        create(key).waiting(timeout, interruptHandler, actionBeforeWaitSynchronized, actionAfterWaitSynchronized);
    }

    public Set<String> getKeys() {
        return this.waitingLockMap.keySet();
    }

    private WakeupLock create(String key) {
        return create(key, this.defaultLockFactory, (k) -> {
        });
    }

    private WakeupLock create(String key, Function<String, WakeupLock> lockFactory, Consumer<String> noCreateAction) {
        AtomicBoolean created = new AtomicBoolean(false);
        WakeupLock lock = this.waitingLockMap.computeIfAbsent(key, k -> {
            created.set(true);
            return lockFactory.apply(k);
        });

        if (!created.get()) {
            noCreateAction.accept(key);
        }

        return lock;
    }

}
