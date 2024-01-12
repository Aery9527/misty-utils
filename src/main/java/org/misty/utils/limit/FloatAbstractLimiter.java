package org.misty.utils.limit;

public abstract class FloatAbstractLimiter implements FloatLimiter {

    private final FloatLimitVerifierHandler verifier;

    private final float min;

    private final float max;

    protected FloatAbstractLimiter(FloatLimitVerifierHandler verifier, float min, float max) {
        this.verifier = verifier;
        this.min = min;
        this.max = max;
    }

    public FloatLimitVerifierHandler getVerifier() {
        return verifier;
    }

    @Override
    public float getMin() {
        return this.min;
    }

    @Override
    public float getMax() {
        return this.max;
    }

}
