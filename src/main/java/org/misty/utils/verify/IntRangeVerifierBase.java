package org.misty.utils.verify;

public class IntRangeVerifierBase<DefaultExceptionType extends Exception> extends RangeVerifierBase<DefaultExceptionType> {

    private final int min;

    private final int max;

    public IntRangeVerifierBase(int min, int max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        super(thrownFactory);
        Verifier.requireIntLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
    }

    /**
     * {@link #requireInclusive(String, int, VerifierThrown)}
     */
    public void requireInclusive(String term, int target) throws DefaultExceptionType {
        requireInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target <= max, mean target must in range [min, max]
     */
    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinInclusiveMaxExclusive(String, int, VerifierThrown)}
     */
    public void requireMinInclusiveMaxExclusive(String term, int target) throws DefaultExceptionType {
        requireMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target < max, mean target must in range [min, max)
     */
    public <ExceptionType extends Exception> void requireMinInclusiveMaxExclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireExclusive(String, int, VerifierThrown)}
     */
    public void requireExclusive(String term, int target) throws DefaultExceptionType {
        requireExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target < max, mean target must in range (min, max)
     */
    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinExclusiveMaxInclusive(String, int, VerifierThrown)}
     */
    public void requireMinExclusiveMaxInclusive(String term, int target) throws DefaultExceptionType {
        requireMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target <= max, mean target must in range (min, max]
     */
    public <ExceptionType extends Exception> void requireMinExclusiveMaxInclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseInclusive(String, int, VerifierThrown)}
     */
    public void refuseInclusive(String term, int target) throws DefaultExceptionType {
        refuseInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target <= max, mean target not in range [min, max]
     */
    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinInclusiveMaxExclusive(String, int, VerifierThrown)}
     */
    public void refuseMinInclusiveMaxExclusive(String term, int target) throws DefaultExceptionType {
        refuseMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target < max, mean target not in range [min, max)
     */
    public <ExceptionType extends Exception> void refuseMinInclusiveMaxExclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseExclusive(String, int, VerifierThrown)}
     */
    public void refuseExclusive(String term, int target) throws DefaultExceptionType {
        refuseExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target < max, mean target not in range (min, max)
     */
    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinExclusiveMaxInclusive(String, int, VerifierThrown)}
     */
    public void refuseMinExclusiveMaxInclusive(String term, int target) throws DefaultExceptionType {
        refuseMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target <= max, mean target not in range (min, max]
     */
    public <ExceptionType extends Exception> void refuseMinExclusiveMaxInclusive(
            String term,
            int target,
            VerifierThrown<Integer, VerifierRangeErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

}
