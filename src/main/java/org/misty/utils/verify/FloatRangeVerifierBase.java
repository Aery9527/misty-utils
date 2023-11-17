package org.misty.utils.verify;

public class FloatRangeVerifierBase<DefaultExceptionType extends Exception> {

    private final float min;

    private final float max;

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public FloatRangeVerifierBase(float min, float max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        Verifier.requireFloatLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
        this.thrownFactory = thrownFactory;
    }

    public void requireInclusive(String term, float target) throws DefaultExceptionType {
        requireInclusive(term, target, this.thrownFactory.getThrower());
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

    public void requireExclusive(String term, float target) throws DefaultExceptionType {
        requireExclusive(term, target, this.thrownFactory.getThrower());
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

    public void refuseInclusive(String term, float target) throws DefaultExceptionType {
        refuseInclusive(term, target, this.thrownFactory.getThrower());
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

    public void refuseExclusive(String term, float target) throws DefaultExceptionType {
        refuseExclusive(term, target, this.thrownFactory.getThrower());
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
