package org.misty.utils.range;

public interface Range {

    static String message(Number lower, Number upper) {
        return "[" + lower + ", " + upper + "]";
    }

    static ShortRangeBuilder shortRangeBuilder() {
        return new ShortRangeBuilder();
    }

    static IntRangeBuilder intRangeBuilder() {
        return new IntRangeBuilder();
    }

    static LongRangeBuilder longRangeBuilder() {
        return new LongRangeBuilder();
    }

    static FloatRangeBuilder floatRangeBuilder() {
        return new FloatRangeBuilder();
    }

    static DoubleRangeBuilder doubleRangeBuilder() {
        return new DoubleRangeBuilder();
    }

    static BigDecimalRangeBuilder bigDecimalRangeBuilder() {
        return new BigDecimalRangeBuilder();
    }

    static BigIntegerRangeBuilder bigIntegerRangeBuilder() {
        return new BigIntegerRangeBuilder();
    }

}
