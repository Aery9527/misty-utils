package org.misty.utils.limit;

public abstract class FloatAbstractLimiter implements FloatLimiter {

    private final FloatLimitVerifierHandler verifier;

    protected FloatAbstractLimiter(FloatLimitVerifierHandler verifier) {
        this.verifier = verifier;
    }

    public FloatLimitVerifierHandler getVerifier() {
        return verifier;
    }

}
