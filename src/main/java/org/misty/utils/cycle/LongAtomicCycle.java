package org.misty.utils.cycle;

import java.util.concurrent.atomic.AtomicLong;

public class LongAtomicCycle extends LongAbstractCycle {

    private final AtomicLong value = new AtomicLong();

    public LongAtomicCycle(LongCycleBuilder builder) {
        super(builder);
        this.value.set(builder.getInitValue());
    }

    @Override
    public String toString() {
        return super.toString() + "(atomic)";
    }

    @Override
    public long get() {
        return this.value.get();
    }

    @Override
    public long getAndNext() {
        return this.value.getAndUpdate(super::next);
    }

    @Override
    public long nextAndGet() {
        return this.value.updateAndGet(super::next);
    }

}
