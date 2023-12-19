package org.misty.utils.range;

public class LongRangePreset implements LongRange {

    private final long lower;

    private final long upper;

    private final long gap;

    private final String rangeMsg;

    public LongRangePreset(long lower, long upper) {
        this.lower = lower;
        this.upper = upper;
        this.gap = upper - lower + 1;
        this.rangeMsg = getClass().getSimpleName() + Range.message(lower, upper);
    }

    @Override
    public String toString() {
        return rangeMsg;
    }

    @Override
    public long getLower() {
        return this.lower;
    }

    @Override
    public long getUpper() {
        return this.upper;
    }

    @Override
    public boolean inRange(long value) {
        return value >= this.lower && value <= this.upper;
    }

    @Override
    public boolean outRange(long value) {
        return !inRange(value);
    }

    @Override
    public long random() {
        return (long) (Math.random() * this.gap) + this.lower;
    }

}
