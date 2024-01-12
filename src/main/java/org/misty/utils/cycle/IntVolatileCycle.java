package org.misty.utils.cycle;

public class IntVolatileCycle extends IntAbstractCycle {

    private volatile int value;

    public IntVolatileCycle(IntCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(volatile)";
    }

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public int getAndNext() {
        int oldValue = this.value;
        this.value = super.next(oldValue);
        return oldValue;
    }

    @Override
    public int nextAndGet() {
        this.value = super.next(this.value);
        return this.value;
    }

}
