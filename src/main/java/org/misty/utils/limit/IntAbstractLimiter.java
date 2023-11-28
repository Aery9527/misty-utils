package org.misty.utils.limit;

public abstract class IntAbstractLimiter implements IntLimiter {

    private final IntLimitVerifierHandler verifier;

    protected IntAbstractLimiter(IntLimitVerifierHandler verifier) {
        this.verifier = verifier;
    }

    public IntLimitVerifierHandler getVerifier() {
        return verifier;
    }

}
