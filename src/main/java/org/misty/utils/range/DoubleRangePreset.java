package org.misty.utils.range;

public class DoubleRangePreset implements DoubleRange {

    private final double lower;

    private final double upper;

    private final double gap;

    private final String rangeMsg;

    public DoubleRangePreset(double lower, double upper) {
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
    public double getLower() {
        return this.lower;
    }

    @Override
    public double getUpper() {
        return this.upper;
    }

    @Override
    public boolean inRange(double value) {
        return value >= this.lower && value <= this.upper;
    }

    @Override
    public boolean outRange(double value) {
        return !inRange(value);
    }

    @Override
    public double random() {
        return (Math.random() * this.gap) + this.lower;
    }

}
