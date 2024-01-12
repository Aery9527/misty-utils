package org.misty.utils.cycle;

import java.math.BigInteger;

public class BigIntegerBaseCycle extends BigIntegerAbstractCycle {

    private BigInteger value;

    public BigIntegerBaseCycle(BigIntegerCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(base)";
    }

    @Override
    public BigInteger get() {
        return this.value;
    }

    @Override
    public BigInteger getAndNext() {
        BigInteger oldValue = this.value;
        this.value = super.next(oldValue);
        return oldValue;
    }

    @Override
    public BigInteger nextAndGet() {
        this.value = super.next(this.value);
        return this.value;
    }

}
