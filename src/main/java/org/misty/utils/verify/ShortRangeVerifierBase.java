package org.misty.utils.verify;

public class ShortRangeVerifierBase<DefaultExceptionType extends Exception> extends RangeVerifierBase<DefaultExceptionType> {

    private final short min;

    private final short max;

    public ShortRangeVerifierBase(short min, short max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        super(thrownFactory);
        Verifier.requireShortLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
    }

    /**
     * {@link #requireInclusive(String, short, VerifierThrown)}
     */
    public void requireInclusive(String term, short target) throws DefaultExceptionType {
        requireInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target <= max, mean target must in range [min, max]
     */
    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinInclusiveMaxExclusive(String, short, VerifierThrown)}
     */
    public void requireMinInclusiveMaxExclusive(String term, short target) throws DefaultExceptionType {
        requireMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target < max, mean target must in range [min, max)
     */
    public <ExceptionType extends Exception> void requireMinInclusiveMaxExclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireExclusive(String, short, VerifierThrown)}
     */
    public void requireExclusive(String term, short target) throws DefaultExceptionType {
        requireExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target < max, mean target must in range (min, max)
     */
    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinExclusiveMaxInclusive(String, short, VerifierThrown)}
     */
    public void requireMinExclusiveMaxInclusive(String term, short target) throws DefaultExceptionType {
        requireMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target <= max, mean target must in range (min, max]
     */
    public <ExceptionType extends Exception> void requireMinExclusiveMaxInclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseInclusive(String, short, VerifierThrown)}
     */
    public void refuseInclusive(String term, short target) throws DefaultExceptionType {
        refuseInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target <= max, mean target not in range [min, max]
     */
    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinInclusiveMaxExclusive(String, short, VerifierThrown)}
     */
    public void refuseMinInclusiveMaxExclusive(String term, short target) throws DefaultExceptionType {
        refuseMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target < max, mean target not in range [min, max)
     */
    public <ExceptionType extends Exception> void refuseMinInclusiveMaxExclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseExclusive(String, short, VerifierThrown)}
     */
    public void refuseExclusive(String term, short target) throws DefaultExceptionType {
        refuseExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target < max, mean target not in range (min, max)
     */
    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinExclusiveMaxInclusive(String, short, VerifierThrown)}
     */
    public void refuseMinExclusiveMaxInclusive(String term, short target) throws DefaultExceptionType {
        refuseMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target <= max, mean target not in range (min, max]
     */
    public <ExceptionType extends Exception> void refuseMinExclusiveMaxInclusive(
            String term,
            short target,
            VerifierThrown<Short, VerifierRangeErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    public short getMin() {
        return min;
    }

    public short getMax() {
        return max;
    }

}
