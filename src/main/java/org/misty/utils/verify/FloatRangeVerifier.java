package org.misty.utils.verify;

public class FloatRangeVerifier extends Verifier {

    private final float min;

    private final float max;

    public FloatRangeVerifier(float min, float max) {
        Verifier.requireFloatLessThanInclusive("min", min, "max", max);
        this.min = min;
        this.max = max;
    }

    public void requireInclusive(String term, float target) throws IllegalArgumentException {
        requireInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            float target,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, float target) throws IllegalArgumentException {
        requireExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            float target,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, float target) throws IllegalArgumentException {
        refuseInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            float target,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, float target) throws IllegalArgumentException {
        refuseExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            float target,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

}
