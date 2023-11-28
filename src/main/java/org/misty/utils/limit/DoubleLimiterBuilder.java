package org.misty.utils.limit;

import org.misty.utils.verify.DoubleRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierErrorMsg;

public class DoubleLimiterBuilder extends
        AbstractLimiterBuilder<
                Double,
                DoubleLimitVerifierHandler,
                DoubleRangeVerifier,
                DoubleLimiter,
                DoubleMildLimiter,
                DoubleLimiterBuilder
                > {

    private boolean acceptUnlimited = false;

    @Override
    protected void verifyMinLessThanMax(Double min, Double max) throws IllegalArgumentException {
        Verifier.requireDoubleLessThanInclusive("min", min, "max", max);
    }

    @Override
    protected DoubleRangeVerifier buildRangeVerifier(Double min, Double max) {
        return Verifier.ofRange(min, max);
    }

    @Override
    protected DoubleLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, Double max, LimiterThrown limiterThrown) {
        double doubleMax = max;
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireDoubleLessThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, doubleMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireDoubleLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, doubleMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireDoubleLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, doubleMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected DoubleLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, Double max, LimiterThrown limiterThrown) {
        double doubleMax = max;
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireDoubleLessThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, doubleMax, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireDoubleLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, doubleMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireDoubleLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, doubleMax, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected DoubleLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, Double min, LimiterThrown limiterThrown) {
        double doubleMin = min;
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireDoubleMoreThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, doubleMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireDoubleMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, doubleMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireDoubleMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, doubleMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected DoubleLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, Double min, LimiterThrown limiterThrown) {
        double doubleMin = min;
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
            Verifier.requireDoubleMoreThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, doubleMin, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireDoubleMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, doubleMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireDoubleMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, doubleMin, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        }, this.acceptUnlimited);
    }

    @Override
    protected DoubleLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, DoubleRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected DoubleLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, DoubleRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected DoubleLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, DoubleRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected DoubleLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, DoubleRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new DoubleLimitVerifierHandler(targetTerm, limiterThrown, target -> { // setVerifier
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
    protected DoubleLimiter buildBaseLimiter(DoubleLimitVerifierHandler verifier, Double initValue) {
        return new DoubleBaseLimiter(verifier, initValue);
    }

    @Override
    protected DoubleLimiter buildVolatileLimiter(DoubleLimitVerifierHandler verifier, Double initValue) {
        return new DoubleVolatileLimiter(verifier, initValue);
    }

    @Override
    protected DoubleLimiter buildAtomicLimiter(DoubleLimitVerifierHandler verifier, Double initValue) {
        return new DoubleAtomicLimiter(verifier, initValue);
    }

    @Override
    protected DoubleMildLimiter wrapMildLimiter(DoubleLimiter limiter) {
        return new DoubleMildLimiter(limiter);
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Double> error, String targetTerm) {
        limiterThrown.thrown(targetTerm + " " + error.getErrorMsg());
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<Double> error, String targetTerm, Double target, String operator, Double operate) {
        limiterThrown.thrown(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, target, operator, operate, error.getErrorMsg()));
    }

    public DoubleLimiterBuilder giveAcceptUnlimited(boolean acceptUnlimited) {
        this.acceptUnlimited = acceptUnlimited;
        return this;
    }

    public DoubleLimiterBuilder NotAcceptUnlimited() {
        this.acceptUnlimited = false;
        return this;
    }

    public DoubleLimiterBuilder acceptUnlimited() {
        this.acceptUnlimited = true;
        return this;
    }

}
