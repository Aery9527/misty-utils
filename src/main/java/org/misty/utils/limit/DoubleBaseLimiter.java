package org.misty.utils.limit;

public class DoubleBaseLimiter extends DoubleAbstractLimiter {

    private double value;

    public DoubleBaseLimiter(DoubleLimitVerifierHandler verifier, Double min, Double max, double initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public double get() {
        return this.value;
    }

    @Override
    public void set(double value) {
        super.getVerifier().verifySet(value);
        this.value = value;
    }

    @Override
    public double plus(double plus) {
        this.value = super.getVerifier().verifyPlus(this.value, plus);
        return this.value;
    }

    @Override
    public double minus(double minus) {
        this.value = super.getVerifier().verifyMinus(this.value, minus);
        return this.value;
    }

}
