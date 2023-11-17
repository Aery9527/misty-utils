package org.misty.utils.verify;

public class IntRangeVerifierBase<DefaultExceptionType extends Exception> {

    private final int min;

    private final int max;

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public IntRangeVerifierBase(int min, int max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        Verifier.requireIntLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
        this.thrownFactory = thrownFactory;
    }

    public void requireInclusive(String term, int target) throws DefaultExceptionType {
        requireInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE));
        }
    }

    public void requireExclusive(String term, int target) throws DefaultExceptionType {
        requireExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE));
        }
    }

    public void refuseInclusive(String term, int target) throws DefaultExceptionType {
        refuseInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE));
        }
    }

    public void refuseExclusive(String term, int target) throws DefaultExceptionType {
        refuseExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE));
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

}
