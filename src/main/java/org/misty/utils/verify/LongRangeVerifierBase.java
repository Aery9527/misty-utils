package org.misty.utils.verify;

public class LongRangeVerifierBase<DefaultExceptionType extends Exception> {

    private final long min;

    private final long max;

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public LongRangeVerifierBase(long min, long max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        Verifier.requireLongLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
        this.thrownFactory = thrownFactory;
    }

    public void requireInclusive(String term, long target) throws DefaultExceptionType {
        requireInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, long target) throws DefaultExceptionType {
        requireExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, long target) throws DefaultExceptionType {
        refuseInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, long target) throws DefaultExceptionType {
        refuseExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            long target,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

}
