package org.misty.utils.limit;

import java.math.BigDecimal;

public abstract class BigDecimalAbstractLimiter implements BigDecimalLimiter {

    private final BigDecimalLimitVerifierHandler verifier;

    private final BigDecimal min;

    private final BigDecimal max;

    protected BigDecimalAbstractLimiter(BigDecimalLimitVerifierHandler verifier, BigDecimal min, BigDecimal max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public BigDecimalLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public BigDecimal getMin() {
        return this.min;
    }

    @Override
    public BigDecimal getMax() {
        return this.max;
    }

}
