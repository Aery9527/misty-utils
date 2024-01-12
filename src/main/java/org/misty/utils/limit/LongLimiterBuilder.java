package org.misty.utils.limit;

import org.misty.utils.verify.LongRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierErrorMsg;

public class LongLimiterBuilder extends
        AbstractLimiterBuilder<
                Long,
                LongLimitVerifierHandler,
                LongRangeVerifier,
                LongLimiter,
                LongMildLimiter,
                LongLimiterBuilder
                > {

    @Override
    protected void verifyMinLessThanMax(Long min, Long max) throws IllegalArgumentException {
        Verifier.requireLongLessThanInclusive("min", min, "max", max);
    }

    @Override
    protected LongRangeVerifier buildRangeVerifier(Long min, Long max) {
        return Verifier.ofRange(min, max);
    }

    @Override
    protected LongLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, Long max, LimiterThrown limiterThrown) {
        long longMax = max;
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireLongLessThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, longMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireLongLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, longMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireLongLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, longMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected LongLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, Long max, LimiterThrown limiterThrown) {
        long longMax = max;
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireLongLessThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, longMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireLongLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, longMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireLongLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, longMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected LongLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, Long min, LimiterThrown limiterThrown) {
        long longMin = min;
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireLongMoreThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, longMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireLongMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, longMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireLongMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, longMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected LongLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, Long min, LimiterThrown limiterThrown) {
        long longMin = min;
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireLongMoreThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, longMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireLongMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, longMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireLongMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, longMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected LongLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, LongRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected LongLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, LongRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected LongLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, LongRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected LongLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, LongRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new LongLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected LongLimiter buildBaseLimiter(LongLimitVerifierHandler verifier, Long initValue) {
        return new LongBaseLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected LongLimiter buildVolatileLimiter(LongLimitVerifierHandler verifier, Long initValue) {
        return new LongVolatileLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected LongLimiter buildAtomicLimiter(LongLimitVerifierHandler verifier, Long initValue) {
        return new LongAtomicLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected LongMildLimiter wrapMildLimiter(LongLimiter limiter) {
        return new LongMildLimiter(limiter);
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Long> error, String targetTerm) {
        limiterThrown.thrown(targetTerm + " " + error.getErrorMsg());
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Long> error, String targetTerm, Long target, String operator, Long operate) {
        limiterThrown.thrown(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, target, operator, operate, error.getErrorMsg()));
    }

}
