package org.misty.utils.limit;

import org.misty.utils.verify.ShortRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierErrorMsg;

public class ShortLimiterBuilder extends
        AbstractLimiterBuilder<
                Short,
                ShortLimitVerifierHandler,
                ShortRangeVerifier,
                ShortLimiter,
                ShortMildLimiter,
                ShortLimiterBuilder
                > {

    @Override
    protected void verifyMinLessThanMax(Short min, Short max) throws IllegalArgumentException {
        Verifier.requireShortLessThanInclusive("min", min, "max", max);
    }

    @Override
    protected ShortRangeVerifier buildRangeVerifier(Short min, Short max) {
        return Verifier.ofRange(min, max);
    }

    @Override
    protected ShortLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, Short max, LimiterThrown limiterThrown) {
        short shortMax = max;
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireShortLessThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, shortMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireShortLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, shortMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireShortLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, shortMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected ShortLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, Short max, LimiterThrown limiterThrown) {
        short shortMax = max;
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireShortLessThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, shortMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireShortLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, shortMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireShortLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, shortMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected ShortLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, Short min, LimiterThrown limiterThrown) {
        short shortMin = min;
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireShortMoreThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, shortMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireShortMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, shortMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireShortMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, shortMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected ShortLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, Short min, LimiterThrown limiterThrown) {
        short shortMin = min;
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireShortMoreThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, shortMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireShortMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, shortMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireShortMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, shortMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected ShortLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, ShortRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected ShortLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, ShortRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected ShortLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, ShortRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected ShortLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, ShortRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new ShortLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected ShortLimiter buildBaseLimiter(ShortLimitVerifierHandler verifier, Short initValue) {
        return new ShortBaseLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected ShortLimiter buildVolatileLimiter(ShortLimitVerifierHandler verifier, Short initValue) {
        return new ShortVolatileLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected ShortLimiter buildAtomicLimiter(ShortLimitVerifierHandler verifier, Short initValue) {
        return new ShortAtomicLimiter(verifier, getMin(), getMax(), initValue);
    }

    @Override
    protected ShortMildLimiter wrapMildLimiter(ShortLimiter limiter) {
        return new ShortMildLimiter(limiter);
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Short> error, String targetTerm) {
        limiterThrown.thrown(targetTerm + " " + error.getErrorMsg());
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Short> error, String targetTerm, Short target, String operator, Short operate) {
        limiterThrown.thrown(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, target, operator, operate, error.getErrorMsg()));
    }

}
