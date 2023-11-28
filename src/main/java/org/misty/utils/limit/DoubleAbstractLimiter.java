package org.misty.utils.limit;

public abstract class DoubleAbstractLimiter implements DoubleLimiter {

    private final DoubleLimitVerifierHandler verifier;

    protected DoubleAbstractLimiter(DoubleLimitVerifierHandler verifier) {
        this.verifier = verifier;
    }

    public DoubleLimitVerifierHandler getVerifier() {
        return verifier;
    }

}
