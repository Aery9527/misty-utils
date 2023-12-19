package org.misty.utils.range;

public class ShortRangePreset implements ShortRange {

    private final short lower;

    private final short upper;

    private final short gap;

    private final String rangeMsg;

    public ShortRangePreset(short lower, short upper) {
        this.lower = lower;
        this.upper = upper;
        this.gap = (short) (upper - lower + 1);
        this.rangeMsg = getClass().getSimpleName() + Range.message(lower, upper);
    }

    @Override
    public String toString() {
        return rangeMsg;
    }

    @Override
    public short getLower() {
        return this.lower;
    }

    @Override
    public short getUpper() {
        return this.upper;
    }

    @Override
    public boolean inRange(short value) {
        return value >= this.lower && value <= this.upper;
    }

    @Override
    public boolean outRange(short value) {
        return !inRange(value);
    }

    @Override
    public short random() {
        return (short) ((Math.random() * this.gap) + this.lower);
    }

}
