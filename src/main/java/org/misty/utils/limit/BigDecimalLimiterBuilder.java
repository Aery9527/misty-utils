package org.misty.utils.limit;

import org.misty.utils.verify.BigDecimalRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierErrorMsg;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalLimiterBuilder extends
        AbstractLimiterBuilder<
                BigDecimal,
                BigDecimalLimitVerifierHandler,
                BigDecimalRangeVerifier,
                BigDecimalLimiter,
                BigDecimalMildLimiter,
                BigDecimalLimiterBuilder
                > {

    private int scale = 2;

    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    @Override
    protected void verifyMinLessThanMax(BigDecimal min, BigDecimal max) throws IllegalArgumentException {
        Verifier.requireBigDecimalLessThanInclusive("min", min, "max", max);
    }

    @Override
    protected BigDecimalRangeVerifier buildRangeVerifier(BigDecimal min, BigDecimal max) {
        return Verifier.ofRange(min, max);
    }

    @Override
    protected BigDecimalLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, BigDecimal max, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
            Verifier.requireBigDecimalLessThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigDecimalLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigDecimalLessThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigDecimalLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, BigDecimal max, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
            Verifier.requireBigDecimalLessThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigDecimalLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigDecimalLessThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MAX_LIMIT_TERM, max, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigDecimalLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, BigDecimal min, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
            Verifier.requireBigDecimalMoreThanInclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigDecimalMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigDecimalMoreThanInclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigDecimalLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, BigDecimal min, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
            Verifier.requireBigDecimalMoreThanExclusive(Limiter.ErrorMsgFormat.SET_TERM, target, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm);
            });
        }, (target, plus, result) -> { // plusVerifier
            Verifier.requireBigDecimalMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "+", plus);
            });
        }, (target, minus, result) -> { // minusVerifier
            Verifier.requireBigDecimalMoreThanExclusive(Limiter.ErrorMsgFormat.RESULT_TERM, result, Limiter.ErrorMsgFormat.MIN_LIMIT_TERM, min, error -> {
                thrown(limiterThrown, error, targetTerm, target, "-", minus);
            });
        });
    }

    @Override
    protected BigDecimalLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, BigDecimalRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
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
    protected BigDecimalLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, BigDecimalRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
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
    protected BigDecimalLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, BigDecimalRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
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
    protected BigDecimalLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, BigDecimalRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
        return new BigDecimalLimitVerifierHandler(this.scale, this.roundingMode, target -> { // setVerifier
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
    protected BigDecimalLimiter buildBaseLimiter(BigDecimalLimitVerifierHandler verifier, BigDecimal initValue) {
        return new BigDecimalBaseLimiter(verifier, initValue);
    }

    @Override
    protected BigDecimalLimiter buildVolatileLimiter(BigDecimalLimitVerifierHandler verifier, BigDecimal initValue) {
        return new BigDecimalVolatileLimiter(verifier, initValue);
    }

    @Override
    protected BigDecimalLimiter buildAtomicLimiter(BigDecimalLimitVerifierHandler verifier, BigDecimal initValue) {
        return new BigDecimalAtomicLimiter(verifier, initValue);
    }

    @Override
    protected BigDecimalMildLimiter wrapMildLimiter(BigDecimalLimiter limiter) {
        return new BigDecimalMildLimiter(limiter);
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<BigDecimal> error, String targetTerm) {
        limiterThrown.thrown(targetTerm + " " + error.getErrorMsg());
    }

    private void thrown(LimiterThrown limiterThrown, VerifierErrorMsg<BigDecimal> error, String targetTerm, BigDecimal target, String operator, BigDecimal operate) {
        limiterThrown.thrown(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, target, operator, operate, error.getErrorMsg()));
    }

    public BigDecimalLimiterBuilder withScale(int scale, RoundingMode roundingMode) {
        BigDecimal.ONE.setScale(scale, roundingMode); // pre-check
        this.scale = scale;
        this.roundingMode = roundingMode;
        return this;
    }

}
