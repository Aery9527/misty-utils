package org.misty.utils.limit;

import java.util.concurrent.atomic.AtomicReference;

public class DoubleAtomicLimiter extends DoubleAbstractLimiter {

    private final AtomicReference<Double> value = new AtomicReference<>();

    public DoubleAtomicLimiter(DoubleLimitVerifierHandler verifier, Double min, Double max, double initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public double get() {
        return this.value.get();
    }

    @Override
    public void set(double value) {
        super.getVerifier().verifySet(value);
        this.value.set(value);
    }

    @Override
    public double plus(double plus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyPlus(current, plus));
    }

    @Override
    public double minus(double minus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyMinus(current, minus));
    }

}
