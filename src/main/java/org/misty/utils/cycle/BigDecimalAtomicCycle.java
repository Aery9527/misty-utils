package org.misty.utils.cycle;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class BigDecimalAtomicCycle extends BigDecimalAbstractCycle {

    private final AtomicReference<BigDecimal> value = new AtomicReference<>();

    public BigDecimalAtomicCycle(BigDecimalCycleBuilder builder) {
        super(builder);
        this.value.set(builder.getInitValue());
    }

    @Override
    public String toString() {
        return super.toString() + "(atomic)";
    }

    @Override
    public BigDecimal get() {
        return this.value.get();
    }

    @Override
    public BigDecimal getAndNext() {
        return this.value.getAndUpdate(super::next);
    }

    @Override
    public BigDecimal nextAndGet() {
        return this.value.updateAndGet(super::next);
    }

}
