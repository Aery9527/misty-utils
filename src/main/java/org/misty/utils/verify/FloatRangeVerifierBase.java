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
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE));
        }
    }

    public void requireExclusive(String term, float target) throws DefaultExceptionType {
        requireExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE));
        }
    }

    public void refuseInclusive(String term, float target) throws DefaultExceptionType {
        refuseInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE));
        }
    }

    public void refuseExclusive(String term, float target) throws DefaultExceptionType {
        refuseExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE));
        }
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

}
