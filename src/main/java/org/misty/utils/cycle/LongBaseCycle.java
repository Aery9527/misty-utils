package org.misty.utils.cycle;

public class LongBaseCycle extends LongAbstractCycle {

    private long value;

    public LongBaseCycle(LongCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(base)";
    }

    @Override
    public long get() {
        return this.value;
    }

    @Override
    public long getAndNext() {
        long oldValue = this.value;
        this.value = super.next(oldValue);
        return oldValue;
    }

    @Override
    public long nextAndGet() {
        this.value = super.next(this.value);
        return this.value;
    }

}
