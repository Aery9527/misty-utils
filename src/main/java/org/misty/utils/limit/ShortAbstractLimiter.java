package org.misty.utils.limit;

public abstract class ShortAbstractLimiter implements ShortLimiter {

    private final ShortLimitVerifierHandler verifier;

    protected ShortAbstractLimiter(ShortLimitVerifierHandler verifier) {
        this.verifier = verifier;
    }

    public ShortLimitVerifierHandler getVerifier() {
        return verifier;
    }

}
