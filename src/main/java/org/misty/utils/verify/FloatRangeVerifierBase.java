package org.misty.utils.verify;

public class FloatRangeVerifierBase<DefaultExceptionType extends Exception> extends RangeVerifierBase<DefaultExceptionType> {

    private final float min;

    private final float max;

    public FloatRangeVerifierBase(String title, float min, float max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        super(title, thrownFactory);
        Verifier.requireFloatLessThanInclusive(title, "min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
    }

    /**
     * {@link #requireInclusive(String, float, VerifierThrown)}
     */
    public void requireInclusive(String term, float target) throws DefaultExceptionType {
        requireInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target <= max, mean target must in range [min, max]
     */
    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinInclusiveMaxExclusive(String, float, VerifierThrown)}
     */
    public void requireMinInclusiveMaxExclusive(String term, float target) throws DefaultExceptionType {
        requireMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target < max, mean target must in range [min, max)
     */
    public <ExceptionType extends Exception> void requireMinInclusiveMaxExclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireExclusive(String, float, VerifierThrown)}
     */
    public void requireExclusive(String term, float target) throws DefaultExceptionType {
        requireExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target < max, mean target must in range (min, max)
     */
    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinExclusiveMaxInclusive(String, float, VerifierThrown)}
     */
    public void requireMinExclusiveMaxInclusive(String term, float target) throws DefaultExceptionType {
        requireMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target <= max, mean target must in range (min, max]
     */
    public <ExceptionType extends Exception> void requireMinExclusiveMaxInclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseInclusive(String, float, VerifierThrown)}
     */
    public void refuseInclusive(String term, float target) throws DefaultExceptionType {
        refuseInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target <= max, mean target not in range [min, max]
     */
    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinInclusiveMaxExclusive(String, float, VerifierThrown)}
     */
    public void refuseMinInclusiveMaxExclusive(String term, float target) throws DefaultExceptionType {
        refuseMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target < max, mean target not in range [min, max)
     */
    public <ExceptionType extends Exception> void refuseMinInclusiveMaxExclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseExclusive(String, float, VerifierThrown)}
     */
    public void refuseExclusive(String term, float target) throws DefaultExceptionType {
        refuseExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target < max, mean target not in range (min, max)
     */
    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinExclusiveMaxInclusive(String, float, VerifierThrown)}
     */
    public void refuseMinExclusiveMaxInclusive(String term, float target) throws DefaultExceptionType {
        refuseMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target <= max, mean target not in range (min, max]
     */
    public <ExceptionType extends Exception> void refuseMinExclusiveMaxInclusive(
            String term,
            float target,
            VerifierThrown<Float, VerifierRangeErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

}
