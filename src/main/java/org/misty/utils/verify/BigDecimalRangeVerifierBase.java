package org.misty.utils.verify;

import java.math.BigDecimal;

public class BigDecimalRangeVerifierBase<DefaultExceptionType extends Exception> extends RangeVerifierBase<DefaultExceptionType> {

    private final BigDecimal min;

    private final BigDecimal max;

    public BigDecimalRangeVerifierBase(BigDecimal min, BigDecimal max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        super(thrownFactory);
        Verifier.requireBigDecimalLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
    }

    /**
     * {@link #requireInclusive(String, BigDecimal, VerifierThrown)}
     */
    public void requireInclusive(String term, BigDecimal target) throws DefaultExceptionType {
        requireInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target <= max, mean target must in range [min, max]
     */
    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) < 0 || target.compareTo(this.max) > 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinInclusiveMaxExclusive(String, BigDecimal, VerifierThrown)}
     */
    public void requireMinInclusiveMaxExclusive(String term, BigDecimal target) throws DefaultExceptionType {
        requireMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target < max, mean target must in range [min, max)
     */
    public <ExceptionType extends Exception> void requireMinInclusiveMaxExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) < 0 || target.compareTo(this.max) >= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireExclusive(String, BigDecimal, VerifierThrown)}
     */
    public void requireExclusive(String term, BigDecimal target) throws DefaultExceptionType {
        requireExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target < max, mean target must in range (min, max)
     */
    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) <= 0 || target.compareTo(this.max) >= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinExclusiveMaxInclusive(String, BigDecimal, VerifierThrown)}
     */
    public void requireMinExclusiveMaxInclusive(String term, BigDecimal target) throws DefaultExceptionType {
        requireMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target <= max, mean target must in range (min, max]
     */
    public <ExceptionType extends Exception> void requireMinExclusiveMaxInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) <= 0 || target.compareTo(this.max) > 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseInclusive(String, BigDecimal, VerifierThrown)}
     */
    public void refuseInclusive(String term, BigDecimal target) throws DefaultExceptionType {
        refuseInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target <= max, mean target not in range [min, max]
     */
    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) >= 0 && target.compareTo(this.max) <= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinInclusiveMaxExclusive(String, BigDecimal, VerifierThrown)}
     */
    public void refuseMinInclusiveMaxExclusive(String term, BigDecimal target) throws DefaultExceptionType {
        refuseMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target < max, mean target not in range [min, max)
     */
    public <ExceptionType extends Exception> void refuseMinInclusiveMaxExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) >= 0 && target.compareTo(this.max) < 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseExclusive(String, BigDecimal, VerifierThrown)}
     */
    public void refuseExclusive(String term, BigDecimal target) throws DefaultExceptionType {
        refuseExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target < max, mean target not in range (min, max)
     */
    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) > 0 && target.compareTo(this.max) < 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinExclusiveMaxInclusive(String, BigDecimal, VerifierThrown)}
     */
    public void refuseMinExclusiveMaxInclusive(String term, BigDecimal target) throws DefaultExceptionType {
        refuseMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target <= max, mean target not in range (min, max]
     */
    public <ExceptionType extends Exception> void refuseMinExclusiveMaxInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, VerifierRangeErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) > 0 && target.compareTo(this.max) <= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }

}
