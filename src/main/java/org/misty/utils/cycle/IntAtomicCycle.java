package org.misty.utils.cycle;

import java.util.concurrent.atomic.AtomicInteger;

public class IntAtomicCycle extends IntAbstractCycle {

    private final AtomicInteger value = new AtomicInteger();

    public IntAtomicCycle(IntCycleBuilder builder) {
        super(builder);
        this.value.set(builder.getInitValue());
    }

    @Override
    public String toString() {
        return super.toString() + "(atomic)";
    }

    @Override
    public int get() {
        return this.value.get();
    }

    @Override
    public int getAndNext() {
        return this.value.getAndUpdate(super::next);
    }

    @Override
    public int nextAndGet() {
        return this.value.updateAndGet(super::next);
    }

}
