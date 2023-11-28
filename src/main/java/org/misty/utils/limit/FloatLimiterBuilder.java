package org.misty.utils.limit;

import org.misty.utils.verify.FloatRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierErrorMsg;

public class FloatLimiterBuilder extends
        AbstractLimiterBuilder<
                Float,
                FloatLimitVerifierHandler,
                FloatRangeVerifier,
                FloatLimiter,
                FloatMildLimiter,
                FloatLimiterBuilder
                > {

    private boolean acceptUnlimited = false;

    @Override
    protected void verifyMinLessThanMax(Float min, Float max) throws IllegalArgumentException {
        Verifier.requireFloatLessThanInclusive("min", min, "max", max);
    }

    @Override
    protected FloatRangeVerifier buildRangeVerifier(Float min, Float max) {
        return Verifier.ofRange(min, max);
    }

    @Override
    protected FloatLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, Float max, LimiterThrown limiterThrown) {
        float floatMax = max;
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireFloatLessThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, floatMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireFloatLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, floatMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireFloatLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, floatMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, Float max, LimiterThrown limiterThrown) {
        float floatMax = max;
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireFloatLessThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, floatMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireFloatLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, floatMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireFloatLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, floatMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, Float min, LimiterThrown limiterThrown) {
        float floatMin = min;
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireFloatMoreThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, floatMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireFloatMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, floatMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireFloatMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, floatMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, Float min, LimiterThrown limiterThrown) {
        float floatMin = min;
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireFloatMoreThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, floatMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireFloatMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, floatMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireFloatMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, floatMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, FloatRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, FloatRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, FloatRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, FloatRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new FloatLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
        }, this.acceptUnlimited);
    }

    @Override
    protected FloatLimiter buildBaseLimiter(FloatLimitVerifierHandler verifier, Float initValue) {
        return new FloatBaseLimiter(verifier, initValue);
    }

    @Override
    protected FloatLimiter buildVolatileLimiter(FloatLimitVerifierHandler verifier, Float initValue) {
        return new FloatVolatileLimiter(verifier, initValue);
    }

    @Override
    protected FloatLimiter buildAtomicLimiter(FloatLimitVerifierHandler verifier, Float initValue) {
        return new FloatAtomicLimiter(verifier, initValue);
    }

    @Override
    protected FloatMildLimiter wrapMildLimiter(FloatLimiter limiter) {
        return new FloatMildLimiter(limiter);
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Float> error, String targetTerm) {
        limiterThrown.thrown(targetTerm + " " + error.getErrorMsg());
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Float> error, String targetTerm, Float target, String operator, Float operate) {
        limiterThrown.thrown(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, target, operator, operate, error.getErrorMsg()));
    }

    public FloatLimiterBuilder giveAcceptUnlimited(boolean acceptUnlimited) {
        this.acceptUnlimited = acceptUnlimited;
        return this;
    }

    public FloatLimiterBuilder NotAcceptUnlimited() {
        this.acceptUnlimited = false;
        return this;
    }

    public FloatLimiterBuilder acceptUnlimited() {
        this.acceptUnlimited = true;
        return this;
    }

}
