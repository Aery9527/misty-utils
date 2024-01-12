package org.misty.utils.limit;

public abstract class IntAbstractLimiter implements IntLimiter {

    private final IntLimitVerifierHandler verifier;

    private final int min;

    private final int max;

    protected IntAbstractLimiter(IntLimitVerifierHandler verifier, int min, int max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public IntLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public int getMin() {
        return this.min;
    }

    @Override
    public int getMax() {
        return this.max;
    }

}
