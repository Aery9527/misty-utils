package org.misty.utils.lock;

import org.misty.utils.Tracked;

public abstract class WakeupBaseLock implements WakeupLock {

    private final Tracked tracked;

    protected WakeupBaseLock(Tracked tracked) {
        this.tracked = tracked;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + tracked;
    }

    public Tracked getTracked() {
        return tracked;
    }

}
