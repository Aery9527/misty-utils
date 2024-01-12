package org.misty.utils.cycle;

public class ShortBaseCycle extends ShortAbstractCycle {

    private short value;

    public ShortBaseCycle(ShortCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(base)";
    }

    @Override
    public short get() {
        return this.value;
    }

    @Override
    public short getAndNext() {
        short oldValue = this.value;
        this.value = super.next(oldValue);
        return oldValue;
    }

    @Override
    public short nextAndGet() {
        this.value = super.next(this.value);
        return this.value;
    }

}
