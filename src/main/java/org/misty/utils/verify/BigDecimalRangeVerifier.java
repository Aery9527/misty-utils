package org.misty.utils.verify;

import java.math.BigDecimal;

public class BigDecimalRangeVerifier extends Verifier {

    private final BigDecimal min;

    private final BigDecimal max;

    public BigDecimalRangeVerifier(BigDecimal min, BigDecimal max) {
        Verifier.requireBigDecimalLessThanInclusive("min", min, max);
        this.min = min;
        this.max = max;
    }

    public void requireInclusive(String term, BigDecimal target) throws IllegalArgumentException {
        requireInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) < 0 || target.compareTo(this.max) > 0) {
            thrown.thrown(term, target, String.format("\"%s(%s)\" must be in range [%s, %s]", term, target, this.min, this.max));
        }
    }

    public void requireExclusive(String term, BigDecimal target) throws IllegalArgumentException {
        requireExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) <= 0 || target.compareTo(this.max) >= 0) {
            thrown.thrown(term, target, String.format("\"%s(%s)\" must be in range (%s, %s)", term, target, this.min, this.max));
        }
    }

    public void refuseInclusive(String term, BigDecimal target) throws IllegalArgumentException {
        refuseInclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) >= 0 && target.compareTo(this.max) <= 0) {
            thrown.thrown(term, target, String.format("\"%s(%s)\" can't be in range [%s, %s]", term, target, this.min, this.max));
        }
    }

    public void refuseExclusive(String term, BigDecimal target) throws IllegalArgumentException {
        refuseExclusive(term, target, Verifier.INSTANCE.defaultThrown());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            BigDecimal target,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) > 0 && target.compareTo(this.max) < 0) {
            thrown.thrown(term, target, String.format("\"%s(%s)\" can't be in range (%s, %s)", term, target, this.min, this.max));
        }
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }

}
