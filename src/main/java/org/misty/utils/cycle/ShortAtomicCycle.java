package org.misty.utils.cycle;

import java.util.concurrent.atomic.AtomicReference;

public class ShortAtomicCycle extends ShortAbstractCycle {

    private final AtomicReference<Short> value = new AtomicReference<>();

    public ShortAtomicCycle(ShortCycleBuilder builder) {
        super(builder);
        this.value.set(builder.getInitValue());
    }

    @Override
    public String toString() {
        return super.toString() + "(atomic)";
    }

    @Override
    public short get() {
        return this.value.get();
    }

    @Override
    public short getAndNext() {
        return this.value.getAndUpdate(super::next);
    }

    @Override
    public short nextAndGet() {
        return this.value.updateAndGet(super::next);
    }

}
