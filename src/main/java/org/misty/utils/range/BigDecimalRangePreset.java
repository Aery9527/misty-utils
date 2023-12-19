package org.misty.utils.range;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalRangePreset implements BigDecimalRange {

    private final BigDecimal lower;

    private final BigDecimal upper;

    private final BigDecimal gap;

    private final String rangeMsg;

    private final int scale;

    private final RoundingMode roundingMode;

    public BigDecimalRangePreset(BigDecimal lower, BigDecimal upper, int scale, RoundingMode roundingMode) {
        this.lower = lower.setScale(scale, roundingMode);
        this.upper = upper.setScale(scale, roundingMode);
        this.gap = upper.subtract(lower).setScale(scale, roundingMode);
        this.rangeMsg = getClass().getSimpleName() + Range.message(lower, upper);
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    @Override
    public String toString() {
        return rangeMsg;
    }

    @Override
    public BigDecimal getLower() {
        return this.lower;
    }

    @Override
    public BigDecimal getUpper() {
        return this.upper;
    }

    @Override
    public boolean inRange(BigDecimal value) {
        return value.compareTo(this.lower) >= 0 && value.compareTo(this.upper) <= 0;
    }

    @Override
    public boolean outRange(BigDecimal value) {
        return !inRange(value);
    }

    @Override
    public BigDecimal random() {
        return this.gap.multiply(BigDecimal.valueOf(Math.random())).add(this.lower).setScale(this.scale, this.roundingMode);
    }

    public int getScale() {
        return scale;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

}
