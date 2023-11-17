package org.misty.utils.verify;

import java.math.BigDecimal;

public class BigDecimalRangeVerifierBase<DefaultExceptionType extends Exception> {

    private final BigDecimal min;

    private final BigDecimal max;

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public BigDecimalRangeVerifierBase(BigDecimal min, BigDecimal max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        Verifier.requireBigDecimalLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
        this.thrownFactory = thrownFactory;
    }

    public void requireInclusive(String term, BigDecimal target) throws DefaultExceptionType {
        requireInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) < 0 || target.compareTo(this.max) > 0) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, BigDecimal target) throws DefaultExceptionType {
        requireExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) <= 0 || target.compareTo(this.max) >= 0) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, BigDecimal target) throws DefaultExceptionType {
        refuseInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) >= 0 && target.compareTo(this.max) <= 0) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, BigDecimal target) throws DefaultExceptionType {
        refuseExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) > 0 && target.compareTo(this.max) < 0) {
            thrown.thrown(term, target, String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, this.min, this.max));
        }
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }

}
