package org.misty.utils.cycle;

import java.math.BigDecimal;

public class BigDecimalBaseCycle extends BigDecimalAbstractCycle {

    private BigDecimal value;

    public BigDecimalBaseCycle(BigDecimalCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(base)";
    }

    @Override
    public BigDecimal get() {
        return this.value;
    }

    @Override
    public BigDecimal getAndNext() {
        BigDecimal oldValue = this.value;
        this.value = super.next(oldValue);
        return oldValue;
    }

    @Override
    public BigDecimal nextAndGet() {
        this.value = super.next(this.value);
        return this.value;
    }

}
