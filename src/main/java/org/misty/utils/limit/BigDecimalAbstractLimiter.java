package org.misty.utils.limit;

import java.math.BigDecimal;
import java.util.Optional;

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
    public Optional<BigDecimal> getMin() {
        return Optional.ofNullable(this.min);
    }

    @Override
    public Optional<BigDecimal> getMax() {
        return Optional.ofNullable(this.max);
    }

}
