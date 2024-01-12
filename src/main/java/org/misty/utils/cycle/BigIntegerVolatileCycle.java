package org.misty.utils.cycle;

import java.math.BigInteger;

public class BigIntegerVolatileCycle extends BigIntegerAbstractCycle {

    private volatile BigInteger value;

    public BigIntegerVolatileCycle(BigIntegerCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(volatile)";
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
