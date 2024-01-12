package org.misty.utils.limit;

public class FloatBaseLimiter extends FloatAbstractLimiter {

    private float value;

    public FloatBaseLimiter(FloatLimitVerifierHandler verifier, float min, float max, float initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public float get() {
        return this.value;
    }

    @Override
    public void set(float value) {
        super.getVerifier().verifySet(value);
        this.value = value;
    }

    @Override
    public float plus(float plus) {
        this.value = super.getVerifier().verifyPlus(this.value, plus);
        return this.value;
    }

    @Override
    public float minus(float minus) {
        this.value = super.getVerifier().verifyMinus(this.value, minus);
        return this.value;
    }

}
