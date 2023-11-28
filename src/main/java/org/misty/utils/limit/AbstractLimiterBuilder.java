package org.misty.utils.limit;

import org.misty.utils.verify.RangeVerifierBase;
import org.misty.utils.verify.Verifier;

public abstract class AbstractLimiterBuilder<
        TargetType extends Number,
        LimitVerifierType extends LimitVerifier,
        RangeVerifierType extends RangeVerifierBase<?>,
        LimiterType extends Limiter,
        MildLimiterType extends MildLimiter<LimiterType>,
        Self extends AbstractLimiterBuilder<TargetType, LimitVerifierType, RangeVerifierType, LimiterType, MildLimiterType, Self>> {

    public enum DeclareType {
        BASE, VOLATILE, ATOMIC,
    }

    private String targetTerm;

    private TargetType min;

    private boolean minInclusive = true;

    private TargetType max;

    private boolean maxInclusive = true;

    private LimiterThrown limiterThrown;

    private DeclareType declareType = DeclareType.BASE;

    public LimiterType build(TargetType initValue) {
        return build(this.limiterThrown, initValue);
    }

    public MildLimiterType buildMildLimiter(TargetType initValue) {
        LimiterType limiter = build(errorMsg -> {
            throw new MildLimiter.MildException();
        }, initValue);
        return wrapMildLimiter(limiter);
    }

    private LimiterType build(LimiterThrown limiterThrown, TargetType initValue) {
        verify(initValue);
        LimitVerifierType verifier = buildVerifier(limiterThrown);
        return buildLimiter(verifier, this.declareType, initValue);
    }

    private void verify(TargetType initValue) {
        String msgTitle = getClass().getSimpleName() + "(" + this.targetTerm + "): ";

        Verifier.refuseNull("initValue", initValue);
        Verifier.refuseNull("limiterThrown", this.limiterThrown);
        Verifier.refuseNullOrEmpty("targetTerm", this.targetTerm, error -> this.limiterThrown.thrown(msgTitle + error.getErrorMsg()));

        if (this.min == null && this.max == null) {
            this.limiterThrown.thrown(msgTitle + "min and max can not be null at the same time");

        } else if (this.min != null && this.max != null) {
            try {
                verifyMinLessThanMax(this.min, this.max);
            } catch (IllegalArgumentException e) {
                this.limiterThrown.thrown(msgTitle + e.getMessage());
            }
        }
    }

    private LimitVerifierType buildVerifier(LimiterThrown limiterThrown) {
        if (this.min == null) {
            if (this.maxInclusive) {
                return buildMaxLimitInclusiveVerifier(this.targetTerm, this.max, limiterThrown);
            } else {
                return buildMaxLimitExclusiveVerifier(this.targetTerm, this.max, limiterThrown);
            }
        } else if (this.max == null) {
            if (this.minInclusive) {
                return buildMinLimitInclusiveVerifier(this.targetTerm, this.min, limiterThrown);
            } else {
                return buildMinLimitExclusiveVerifier(this.targetTerm, this.min, limiterThrown);
            }
        }

        RangeVerifierType rangeVerifier = buildRangeVerifier(this.min, this.max);

        if (this.minInclusive && this.maxInclusive) {
            return buildRangeInclusiveInclusiveVerifier(this.targetTerm, rangeVerifier, limiterThrown);
        } else if (this.minInclusive && !this.maxInclusive) {
            return buildRangeInclusiveExclusiveVerifier(this.targetTerm, rangeVerifier, limiterThrown);
        } else if (!this.minInclusive && this.maxInclusive) {
            return buildRangeExclusiveInclusiveVerifier(this.targetTerm, rangeVerifier, limiterThrown);
        } else {
            return buildRangeExclusiveExclusiveVerifier(this.targetTerm, rangeVerifier, limiterThrown);
        }
    }

    private LimiterType buildLimiter(LimitVerifierType verifier, DeclareType declareType, TargetType initValue) {
        if (declareType == DeclareType.BASE) {
            return buildBaseLimiter(verifier, initValue);
        } else if (declareType == DeclareType.VOLATILE) {
            return buildVolatileLimiter(verifier, initValue);
        } else {
            return buildAtomicLimiter(verifier, initValue);
        }
    }

    protected abstract void verifyMinLessThanMax(TargetType min, TargetType max) throws IllegalArgumentException;

    protected abstract RangeVerifierType buildRangeVerifier(TargetType min, TargetType max);

    /**
     * limit target <= max
     */
    protected abstract LimitVerifierType buildMaxLimitInclusiveVerifier(String targetTerm, TargetType max, LimiterThrown limiterThrown);

    /**
     * limit target < max
     */
    protected abstract LimitVerifierType buildMaxLimitExclusiveVerifier(String targetTerm, TargetType max, LimiterThrown limiterThrown);

    /**
     * limit target >= min
     */
    protected abstract LimitVerifierType buildMinLimitInclusiveVerifier(String targetTerm, TargetType min, LimiterThrown limiterThrown);

    /**
     * limit target > min
     */
    protected abstract LimitVerifierType buildMinLimitExclusiveVerifier(String targetTerm, TargetType min, LimiterThrown limiterThrown);

    /**
     * limit min <= target <= max
     */
    protected abstract LimitVerifierType buildRangeInclusiveInclusiveVerifier(String targetTerm, RangeVerifierType rangeVerifier, LimiterThrown limiterThrown);

    /**
     * limit min <= target < max
     */
    protected abstract LimitVerifierType buildRangeInclusiveExclusiveVerifier(String targetTerm, RangeVerifierType rangeVerifier, LimiterThrown limiterThrown);

    /**
     * limit min < target < max
     */
    protected abstract LimitVerifierType buildRangeExclusiveExclusiveVerifier(String targetTerm, RangeVerifierType rangeVerifier, LimiterThrown limiterThrown);

    /**
     * limit min < target <= max
     */
    protected abstract LimitVerifierType buildRangeExclusiveInclusiveVerifier(String targetTerm, RangeVerifierType rangeVerifier, LimiterThrown limiterThrown);

    protected abstract LimiterType buildBaseLimiter(LimitVerifierType verifier, TargetType initValue);

    protected abstract LimiterType buildVolatileLimiter(LimitVerifierType verifier, TargetType initValue);

    protected abstract LimiterType buildAtomicLimiter(LimitVerifierType verifier, TargetType initValue);

    protected abstract MildLimiterType wrapMildLimiter(LimiterType limiter);

    public Self giveTargetTerm(String targetTerm) {
        this.targetTerm = targetTerm;
        return (Self) this;
    }

    public Self giveMinLimit(TargetType min, boolean inclusive) {
        this.min = min;
        this.minInclusive = inclusive;
        return (Self) this;
    }

    public Self giveMaxLimit(TargetType max, boolean inclusive) {
        this.max = max;
        this.maxInclusive = inclusive;
        return (Self) this;
    }

    public Self giveThrown(LimiterThrown limiterThrown) {
        this.limiterThrown = limiterThrown;
        return (Self) this;
    }

    public Self withBase() {
        this.declareType = DeclareType.BASE;
        return (Self) this;
    }

    public Self withVolatile() {
        this.declareType = DeclareType.VOLATILE;
        return (Self) this;
    }

    public Self withAtomic() {
        this.declareType = DeclareType.ATOMIC;
        return (Self) this;
    }

    public String getTargetTerm() {
        return targetTerm;
    }

    public TargetType getMin() {
        return min;
    }

    public boolean isMinInclusive() {
        return minInclusive;
    }

    public TargetType getMax() {
        return max;
    }

    public boolean isMaxInclusive() {
        return maxInclusive;
    }

    public LimiterThrown getLimiterThrown() {
        return limiterThrown;
    }

    public DeclareType getDeclareType() {
        return declareType;
    }
}
