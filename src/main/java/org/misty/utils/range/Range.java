package org.misty.utils.range;

public interface Range {

    static String message(Number lower, Number upper) {
        return "[" + lower + ", " + upper + "]";
    }

    static ShortRangeBuilder shortRangeBuilder() {
        return new ShortRangeBuilder("");
    }

    static ShortRangeBuilder shortRangeBuilder(String title) {
        return new ShortRangeBuilder(title);
    }

    static IntRangeBuilder intRangeBuilder() {
        return new IntRangeBuilder("");
    }

    static IntRangeBuilder intRangeBuilder(String title) {
        return new IntRangeBuilder(title);
    }

    static LongRangeBuilder longRangeBuilder() {
        return new LongRangeBuilder("");
    }

    static LongRangeBuilder longRangeBuilder(String title) {
        return new LongRangeBuilder(title);
    }

    static FloatRangeBuilder floatRangeBuilder() {
        return new FloatRangeBuilder("");
    }

    static FloatRangeBuilder floatRangeBuilder(String title) {
        return new FloatRangeBuilder(title);
    }

    static DoubleRangeBuilder doubleRangeBuilder() {
        return new DoubleRangeBuilder("");
    }

    static DoubleRangeBuilder doubleRangeBuilder(String title) {
        return new DoubleRangeBuilder(title);
    }

    static BigDecimalRangeBuilder bigDecimalRangeBuilder() {
        return new BigDecimalRangeBuilder("");
    }

    static BigDecimalRangeBuilder bigDecimalRangeBuilder(String title) {
        return new BigDecimalRangeBuilder(title);
    }

    static BigIntegerRangeBuilder bigIntegerRangeBuilder() {
        return new BigIntegerRangeBuilder("");
    }

    static BigIntegerRangeBuilder bigIntegerRangeBuilder(String title) {
        return new BigIntegerRangeBuilder(title);
    }

}
