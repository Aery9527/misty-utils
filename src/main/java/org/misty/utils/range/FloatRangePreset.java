package org.misty.utils.range;

public class FloatRangePreset implements FloatRange {

    private final float lower;

    private final float upper;

    private final float gap;

    private final String rangeMsg;

    public FloatRangePreset(float lower, float upper) {
        this.lower = lower;
        this.upper = upper;
        this.gap = upper - lower;
        this.rangeMsg = getClass().getSimpleName() + Range.message(lower, upper);
    }

    @Override
    public String toString() {
        return rangeMsg;
    }

    @Override
    public float getLower() {
        return this.lower;
    }

    @Override
    public float getUpper() {
        return this.upper;
    }

    @Override
    public boolean inRange(float value) {
        return value >= this.lower && value <= this.upper;
    }

    @Override
    public boolean outRange(float value) {
        return !inRange(value);
    }

    @Override
    public float random() {
        return (float) (Math.random() * this.gap) + this.lower;
    }

}
