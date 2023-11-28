package org.misty.utils.limit;

public abstract class LongAbstractLimiter implements LongLimiter {

    private final LongLimitVerifierHandler verifier;

    protected LongAbstractLimiter(LongLimitVerifierHandler verifier) {
        this.verifier = verifier;
    }

    public LongLimitVerifierHandler getVerifier() {
        return verifier;
    }

}
