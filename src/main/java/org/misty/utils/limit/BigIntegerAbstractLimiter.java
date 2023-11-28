package org.misty.utils.limit;

public abstract class BigIntegerAbstractLimiter implements BigIntegerLimiter {

    private final BigIntegerLimitVerifierHandler verifier;

    protected BigIntegerAbstractLimiter(BigIntegerLimitVerifierHandler verifier) {
        this.verifier = verifier;
    }

    public BigIntegerLimitVerifierHandler getVerifier() {
        return verifier;
    }

}
