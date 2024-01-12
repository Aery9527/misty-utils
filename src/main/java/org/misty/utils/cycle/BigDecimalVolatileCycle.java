package org.misty.utils.cycle;

import java.math.BigDecimal;

public class BigDecimalVolatileCycle extends BigDecimalAbstractCycle {

    private volatile BigDecimal value;

    public BigDecimalVolatileCycle(BigDecimalCycleBuilder builder) {
        super(builder);
        this.value = builder.getInitValue();
    }

    @Override
    public String toString() {
        return super.toString() + "(volatile)";
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
