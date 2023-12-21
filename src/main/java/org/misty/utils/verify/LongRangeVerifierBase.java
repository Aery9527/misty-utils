package org.misty.utils.verify;

public class LongRangeVerifierBase<DefaultExceptionType extends Exception> extends RangeVerifierBase<DefaultExceptionType> {

    private final long min;

    private final long max;

    public LongRangeVerifierBase(String title, long min, long max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        super(title, thrownFactory);
        Verifier.requireLongLessThanInclusive(title, "min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
    }

    /**
     * {@link #requireInclusive(String, long, VerifierThrown)}
     */
    public void requireInclusive(String term, long target) throws DefaultExceptionType {
        requireInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target <= max, mean target must in range [min, max]
     */
    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinInclusiveMaxExclusive(String, long, VerifierThrown)}
     */
    public void requireMinInclusiveMaxExclusive(String term, long target) throws DefaultExceptionType {
        requireMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target < max, mean target must in range [min, max)
     */
    public <ExceptionType extends Exception> void requireMinInclusiveMaxExclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireExclusive(String, long, VerifierThrown)}
     */
    public void requireExclusive(String term, long target) throws DefaultExceptionType {
        requireExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target < max, mean target must in range (min, max)
     */
    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinExclusiveMaxInclusive(String, long, VerifierThrown)}
     */
    public void requireMinExclusiveMaxInclusive(String term, long target) throws DefaultExceptionType {
        requireMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target <= max, mean target must in range (min, max]
     */
    public <ExceptionType extends Exception> void requireMinExclusiveMaxInclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseInclusive(String, long, VerifierThrown)}
     */
    public void refuseInclusive(String term, long target) throws DefaultExceptionType {
        refuseInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target <= max, mean target not in range [min, max]
     */
    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinInclusiveMaxExclusive(String, long, VerifierThrown)}
     */
    public void refuseMinInclusiveMaxExclusive(String term, long target) throws DefaultExceptionType {
        refuseMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target < max, mean target not in range [min, max)
     */
    public <ExceptionType extends Exception> void refuseMinInclusiveMaxExclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseExclusive(String, long, VerifierThrown)}
     */
    public void refuseExclusive(String term, long target) throws DefaultExceptionType {
        refuseExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target < max, mean target not in range (min, max)
     */
    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinExclusiveMaxInclusive(String, long, VerifierThrown)}
     */
    public void refuseMinExclusiveMaxInclusive(String term, long target) throws DefaultExceptionType {
        refuseMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target <= max, mean target not in range (min, max]
     */
    public <ExceptionType extends Exception> void refuseMinExclusiveMaxInclusive(
            String term,
            long target,
            VerifierThrown<Long, VerifierRangeErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

}
