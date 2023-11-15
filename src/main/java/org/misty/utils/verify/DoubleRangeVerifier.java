package org.misty.utils.verify;

public class DoubleRangeVerifier extends Verifier {

    private final double min;

    private final double max;

    public DoubleRangeVerifier(double min, double max) {
        Verifier.requireDoubleLessThanInclusive("min", min, max);
        this.min = min;
        this.max = max;
    }

    public void requireInclusive(String term, double target) throws IllegalArgumentException {
        requireInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            double target,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(term, target, String.format("\"%s(%f)\" must be in range [%f, %f]", term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, double target) throws IllegalArgumentException {
        requireExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            double target,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(term, target, String.format("\"%s(%f)\" must be in range (%f, %f)", term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, double target) throws IllegalArgumentException {
        refuseInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            double target,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(term, target, String.format("\"%s(%f)\" can't be in range [%f, %f]", term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, double target) throws IllegalArgumentException {
        refuseExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            double target,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(term, target, String.format("\"%s(%f)\" can't be in range (%f, %f)", term, target, this.min, this.max));
        }
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

}
