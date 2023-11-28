package org.misty.utils.limit;

public abstract class BigDecimalAbstractLimiter implements BigDecimalLimiter {

    private final BigDecimalLimitVerifierHandler verifier;

    protected BigDecimalAbstractLimiter(BigDecimalLimitVerifierHandler verifier) {
        this.verifier = verifier;
    }

    public BigDecimalLimitVerifierHandler getVerifier() {
        return verifier;
    }

}
