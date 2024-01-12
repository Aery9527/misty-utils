package org.misty.utils.limit;

import java.math.BigDecimal;

public class BigDecimalVolatileLimiter extends BigDecimalAbstractLimiter {

    private volatile BigDecimal value;

    public BigDecimalVolatileLimiter(BigDecimalLimitVerifierHandler verifier, BigDecimal min, BigDecimal max, BigDecimal initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public BigDecimal get() {
        return this.value;
    }

    @Override
    public void set(BigDecimal value) {
        this.value = super.getVerifier().verifySet(value);
    }

    @Override
    public BigDecimal plus(BigDecimal plus) {
        this.value = super.getVerifier().verifyPlus(this.value, plus);
        return this.value;
    }

    @Override
    public BigDecimal minus(BigDecimal minus) {
        this.value = super.getVerifier().verifyMinus(this.value, minus);
        return this.value;
    }

}
