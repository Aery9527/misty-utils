package org.misty.utils.limit;

import org.misty.utils.verify.IntRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierErrorMsg;

public class IntLimiterBuilder extends
        AbstractLimiterBuilder<
                Integer,
                IntLimitVerifierHandler,
                IntRangeVerifier,
                IntLimiter,
                IntMildLimiter,
                IntLimiterBuilder
                > {

    @Override
    protected void verifyMinLessThanMax(Integer min, Integer max) throws IllegalArgumentException {
        Verifier.requireIntLessThanInclusive("min", min, "max", max);
    }

    @Override
    protected IntRangeVerifier buildRangeVerifier(Integer min, Integer max) {
        return Verifier.ofRange(min, max);
    }

    @Override
    protected IntLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, Integer max, LimiterThrown limiterThrown) {
        int intMax = max;
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireIntLessThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, intMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireIntLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, intMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireIntLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, intMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected IntLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, Integer max, LimiterThrown limiterThrown) {
        int intMax = max;
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireIntLessThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, intMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireIntLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, intMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireIntLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, intMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected IntLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, Integer min, LimiterThrown limiterThrown) {
        int intMin = min;
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireIntMoreThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, intMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireIntMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, intMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireIntMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, intMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected IntLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, Integer min, LimiterThrown limiterThrown) {
        int intMin = min;
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireIntMoreThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, intMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireIntMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, intMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireIntMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, intMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected IntLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected IntLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected IntLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected IntLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new IntLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected IntLimiter buildBaseLimiter(IntLimitVerifierHandler verifier, Integer initValue) {
        return new IntBaseLimiter(verifier, initValue);
    }

    @Override
    protected IntLimiter buildVolatileLimiter(IntLimitVerifierHandler verifier, Integer initValue) {
        return new IntVolatileLimiter(verifier, initValue);
    }

    @Override
    protected IntLimiter buildAtomicLimiter(IntLimitVerifierHandler verifier, Integer initValue) {
        return new IntAtomicLimiter(verifier, initValue);
    }

    @Override
    protected IntMildLimiter wrapMildLimiter(IntLimiter limiter) {
        return new IntMildLimiter(limiter);
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Integer> error, String targetTerm) {
        limiterThrown.thrown(targetTerm + " " + error.getErrorMsg());
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Integer> error, String targetTerm, Integer target, String operator, Integer operate) {
        limiterThrown.thrown(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, target, operator, operate, error.getErrorMsg()));
    }

}
