package org.misty.utils.verify;

public class ShortRangeVerifierBase<DefaultExceptionType extends Exception> {

    private final short min;

    private final short max;

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public ShortRangeVerifierBase(short min, short max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        Verifier.requireShortLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
        this.thrownFactory = thrownFactory;
    }

    public void requireInclusive(String term, short target) throws DefaultExceptionType {
        requireInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE));
        }
    }

    public void requireExclusive(String term, short target) throws DefaultExceptionType {
        requireExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE));
        }
    }

    public void refuseInclusive(String term, short target) throws DefaultExceptionType {
        refuseInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE));
        }
    }

    public void refuseExclusive(String term, short target) throws DefaultExceptionType {
        refuseExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE));
        }
    }

    public short getMin() {
        return min;
    }

    public short getMax() {
        return max;
    }

}
