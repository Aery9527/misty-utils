package org.misty.utils.cycle;

public class IntBaseCycle extends IntAbstractCycle {

    private int value;

    public IntBaseCycle(IntCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(base)";
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
