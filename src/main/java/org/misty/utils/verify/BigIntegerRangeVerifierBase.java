package org.misty.utils.verify;

import java.math.BigInteger;

public class BigIntegerRangeVerifierBase<DefaultExceptionType extends Exception> extends RangeVerifierBase<DefaultExceptionType> {

    private final BigInteger min;

    private final BigInteger max;

    public BigIntegerRangeVerifierBase(BigInteger min, BigInteger max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        super(thrownFactory);
        Verifier.requireBigIntegerLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
    }

    /**
     * {@link #requireInclusive(String, BigInteger, VerifierThrown)}
     */
    public void requireInclusive(String term, BigInteger target) throws DefaultExceptionType {
        requireInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target <= max, mean target must in range [min, max]
     */
    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) < 0 || target.compareTo(this.max) > 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinInclusiveMaxExclusive(String, BigInteger, VerifierThrown)}
     */
    public void requireMinInclusiveMaxExclusive(String term, BigInteger target) throws DefaultExceptionType {
        requireMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target >= min && target < max, mean target must in range [min, max)
     */
    public <ExceptionType extends Exception> void requireMinInclusiveMaxExclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) < 0 || target.compareTo(this.max) >= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireExclusive(String, BigInteger, VerifierThrown)}
     */
    public void requireExclusive(String term, BigInteger target) throws DefaultExceptionType {
        requireExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target < max, mean target must in range (min, max)
     */
    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) <= 0 || target.compareTo(this.max) >= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #requireMinExclusiveMaxInclusive(String, BigInteger, VerifierThrown)}
     */
    public void requireMinExclusiveMaxInclusive(String term, BigInteger target) throws DefaultExceptionType {
        requireMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * require target > min && target <= max, mean target must in range (min, max]
     */
    public <ExceptionType extends Exception> void requireMinExclusiveMaxInclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) <= 0 || target.compareTo(this.max) > 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseInclusive(String, BigInteger, VerifierThrown)}
     */
    public void refuseInclusive(String term, BigInteger target) throws DefaultExceptionType {
        refuseInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target <= max, mean target not in range [min, max]
     */
    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) >= 0 && target.compareTo(this.max) <= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinInclusiveMaxExclusive(String, BigInteger, VerifierThrown)}
     */
    public void refuseMinInclusiveMaxExclusive(String term, BigInteger target) throws DefaultExceptionType {
        refuseMinInclusiveMaxExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target >= min && target < max, mean target not in range [min, max)
     */
    public <ExceptionType extends Exception> void refuseMinInclusiveMaxExclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) >= 0 && target.compareTo(this.max) < 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseExclusive(String, BigInteger, VerifierThrown)}
     */
    public void refuseExclusive(String term, BigInteger target) throws DefaultExceptionType {
        refuseExclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target < max, mean target not in range (min, max)
     */
    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) > 0 && target.compareTo(this.max) < 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE, term, target, min, max)));
        }
    }

    /**
     * {@link #refuseMinExclusiveMaxInclusive(String, BigInteger, VerifierThrown)}
     */
    public void refuseMinExclusiveMaxInclusive(String term, BigInteger target) throws DefaultExceptionType {
        refuseMinExclusiveMaxInclusive(term, target, super.getThrownFactory().getThrower());
    }

    /**
     * refuse target > min && target <= max, mean target not in range (min, max]
     */
    public <ExceptionType extends Exception> void refuseMinExclusiveMaxInclusive(
            String term,
            BigInteger target,
            VerifierThrown<BigInteger, VerifierRangeErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(this.min) > 0 && target.compareTo(this.max) <= 0) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max,
                    String.format(Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE, term, target, min, max)));
        }
    }

    public BigInteger getMin() {
        return min;
    }

    public BigInteger getMax() {
        return max;
    }

}
