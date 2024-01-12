package org.misty.utils.limit;

import java.util.concurrent.atomic.AtomicReference;

public class FloatAtomicLimiter extends FloatAbstractLimiter {

    private final AtomicReference<Float> value = new AtomicReference<>();

    public FloatAtomicLimiter(FloatLimitVerifierHandler verifier, float min, float max, float initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public float get() {
        return this.value.get();
    }

    @Override
    public void set(float value) {
        super.getVerifier().verifySet(value);
        this.value.set(value);
    }

    @Override
    public float plus(float plus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyPlus(current, plus));
    }

    @Override
    public float minus(float minus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyMinus(current, minus));
    }

}
