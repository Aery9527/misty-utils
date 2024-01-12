package org.misty.utils.cycle;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BigIntegerAtomicCycle extends BigIntegerAbstractCycle {

    private final AtomicReference<BigInteger> value = new AtomicReference<>();

    public BigIntegerAtomicCycle(BigIntegerCycleBuilder builder) {
        super(builder);
        this.value.set(builder.getInitValue());
    }

    @Override
    public String toString() {
        return super.toString() + "(atomic)";
    }

    @Override
    public BigInteger get() {
        return this.value.get();
    }

    @Override
    public BigInteger getAndNext() {
        return this.value.getAndUpdate(super::next);
    }

    @Override
    public BigInteger nextAndGet() {
        return this.value.updateAndGet(super::next);
    }

}
