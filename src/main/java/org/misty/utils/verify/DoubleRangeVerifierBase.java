package org.misty.utils.verify;

public class DoubleRangeVerifierBase<DefaultExceptionType extends Exception> extends RangeVerifierBase<DefaultExceptionType> {

    private final double min;

    private final double max;

    public DoubleRangeVerifierBase(String title, double min, double max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        super(title, thrownFactory);
        Verifier.requireDoubleLessThanInclusive(title, "min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
    }

    /**
     * {@link #requireInclusive(String, double, VerifierThrown)}
     */
    public void requireInclusive(String term, double target) throws DefaultExceptionType {
        requireInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target <= max, mean target must in range [min, max]
     */
    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinInclusiveMaxExclusive(String, double, VerifierThrown)}
     */
    public void requireMinInclusiveMaxExclusive(String term, double target) throws DefaultExceptionType {
        requireMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target < max, mean target must in range [min, max)
     */
    public <ExceptionType extends Exception> void requireMinInclusiveMaxExclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireExclusive(String, double, VerifierThrown)}
     */
    public void requireExclusive(String term, double target) throws DefaultExceptionType {
        requireExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target < max, mean target must in range (min, max)
     */
    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinExclusiveMaxInclusive(String, double, VerifierThrown)}
     */
    public void requireMinExclusiveMaxInclusive(String term, double target) throws DefaultExceptionType {
        requireMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target <= max, mean target must in range (min, max]
     */
    public <ExceptionType extends Exception> void requireMinExclusiveMaxInclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseInclusive(String, double, VerifierThrown)}
     */
    public void refuseInclusive(String term, double target) throws DefaultExceptionType {
        refuseInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target <= max, mean target not in range [min, max]
     */
    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinInclusiveMaxExclusive(String, double, VerifierThrown)}
     */
    public void refuseMinInclusiveMaxExclusive(String term, double target) throws DefaultExceptionType {
        refuseMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target < max, mean target not in range [min, max)
     */
    public <ExceptionType extends Exception> void refuseMinInclusiveMaxExclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseExclusive(String, double, VerifierThrown)}
     */
    public void refuseExclusive(String term, double target) throws DefaultExceptionType {
        refuseExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target < max, mean target not in range (min, max)
     */
    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinExclusiveMaxInclusive(String, double, VerifierThrown)}
     */
    public void refuseMinExclusiveMaxInclusive(String term, double target) throws DefaultExceptionType {
        refuseMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target <= max, mean target not in range (min, max]
     */
    public <ExceptionType extends Exception> void refuseMinExclusiveMaxInclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    getTitle() + String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

}
