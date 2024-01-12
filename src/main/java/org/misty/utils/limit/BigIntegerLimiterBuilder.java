package org.misty.utils.limit;

import org.misty.utils.verify.BigIntegerRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierErrorMsg;

import java.math.BigInteger;

public class BigIntegerLimiterBuilder extends
        AbstractLimiterBuilder<
                BigInteger,
                BigIntegerLimitVerifierHandler,
                BigIntegerRangeVerifier,
                BigIntegerLimiter,
                BigIntegerMildLimiter,
                BigIntegerLimiterBuilder
                > {

    @Override
    protected void verifyMinLessThanMax(BigInteger min, BigInteger max) throws IllegalArgumentException {
        Verifier.requireBigIntegerLessThanInclusive("min", min, "max", max);
    }

    @Override
    protected BigIntegerRangeVerifier buildRangeVerifier(BigInteger min, BigInteger max) {
        return Verifier.ofRange(min, max);
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, BigInteger max, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            Verifier.requireBigIntegerLessThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigIntegerLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigIntegerLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, BigInteger max, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            Verifier.requireBigIntegerLessThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigIntegerLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigIntegerLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, BigInteger min, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            Verifier.requireBigIntegerMoreThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigIntegerMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigIntegerMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, BigInteger min, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            Verifier.requireBigIntegerMoreThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigIntegerMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigIntegerMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, BigIntegerRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            rangeVerifier.requireInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            rangeVerifier.requireInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            rangeVerifier.requireInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, BigIntegerRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            rangeVerifier.requireMinInclusiveMaxExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            rangeVerifier.requireMinInclusiveMaxExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            rangeVerifier.requireMinInclusiveMaxExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, BigIntegerRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            rangeVerifier.requireExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            rangeVerifier.requireExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            rangeVerifier.requireExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, BigIntegerRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigIntegerLimitVerifierHandler(target -> { // setVerifier
            rangeVerifier.requireMinExclusiveMaxInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            rangeVerifier.requireMinExclusiveMaxInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            rangeVerifier.requireMinExclusiveMaxInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigIntegerLimiter buildBaseLimiter(BigIntegerLimitVerifierHandler verifier, BigInteger initValue) {
        return new BigIntegerBaseLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected BigIntegerLimiter buildVolatileLimiter(BigIntegerLimitVerifierHandler verifier, BigInteger initValue) {
        return new BigIntegerVolatileLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected BigIntegerLimiter buildAtomicLimiter(BigIntegerLimitVerifierHandler verifier, BigInteger initValue) {
        return new BigIntegerAtomicLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected BigIntegerMildLimiter wrapMildLimiter(BigIntegerLimiter limiter) {
        return new BigIntegerMildLimiter(limiter);
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<BigInteger> error, String targetTerm) {
        limiterThrown.thrown(targetTerm + " " + error.getErrorMsg());
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<BigInteger> error, String targetTerm, BigInteger target, String operator, BigInteger operate) {
        limiterThrown.thrown(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, target, operator, operate, error.getErrorMsg()));
    }

}
