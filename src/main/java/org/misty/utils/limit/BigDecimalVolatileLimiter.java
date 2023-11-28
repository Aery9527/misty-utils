package org.misty.utils.limit;

import java.math.BigDecimal;

public class BigDecimalVolatileLimiter extends BigDecimalAbstractLimiter {

    private volatile BigDecimal value;

    public BigDecimalVolatileLimiter(BigDecimalLimitVerifierHandler verifier, BigDecimal initValue) {
        super(verifier);
        set(initValue);
    }

    @Override
    public BigDecimal get() {
        return this.value;
    }

    @Override
    public void set(BigDecimal value) {
        super.getVerifier().verifySet(value);
        this.value = value;
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
