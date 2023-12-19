package org.misty.utils.range;

public class IntRangePreset implements IntRange {

    private final int lower;

    private final int upper;

    private final int gap;

    private final String rangeMsg;

    public IntRangePreset(int lower, int upper) {
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
    public int getLower() {
        return this.lower;
    }

    @Override
    public int getUpper() {
        return this.upper;
    }

    @Override
    public boolean inRange(int value) {
        return value >= this.lower && value <= this.upper;
    }

    @Override
    public boolean outRange(int value) {
        return !inRange(value);
    }

    @Override
    public int random() {
        return (int) (Math.random() * this.gap) + this.lower;
    }

}
