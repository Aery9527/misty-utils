package org.misty.utils.cycle;

public class ShortVolatileCycle extends ShortAbstractCycle {

    private volatile short value;

    public ShortVolatileCycle(ShortCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(volatile)";
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
