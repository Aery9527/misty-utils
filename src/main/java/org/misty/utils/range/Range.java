package org.misty.utils.range;

import org.misty.utils.verify.Verifier;

public interface Range {

    static String divide(long target, long interval) {
        return divide(target, interval, "%d");
    }

    static String divide(long target, long interval, String format) {
        return divide(target, interval, format, "[", ", ", "]");
    }

    static String divide(long target, long interval, String prefix, String arrive, String suffix) {
        return divide(target, interval, "%d", prefix, arrive, suffix);
    }

    static String divide(long target, long interval, String format, String prefix, String arrive, String suffix) {
        Verifier.requireLongMoreThanInclusive("interval", interval, 1);

        if (target == 0) {
            return prefix + "0" + suffix;
        }

        long quotient = target / interval;
        long remainder = target % interval;

        long start;
        long end;
        if (target > 0) {
            start = ((remainder == 0 ? quotient - 1 : quotient) * interval) + 1;
            end = start + interval - 1;
        } else {
            end = ((remainder == 0 ? quotient + 1 : quotient) * interval) - 1;
            start = end - interval + 1;
        }

        return prefix + String.format(format, start) + arrive + String.format(format, end) + suffix;
    }

    static String divide(double target, double interval, int scale) {
        return divide(target, interval, "%." + scale + "f");
    }

    static String divide(double target, double interval, String format) {
        Verifier.requireDoubleMoreThanInclusive("interval", interval, 1);

        int quotient = (int) (target / interval);

        double start;
        double end;
        if (target >= 0) {
            start = quotient * interval;
        } else {
            double remainder = target % interval;
            start = (remainder == 0 ? quotient : quotient - 1) * interval;
        }

        end = start + interval;

        return "[" + String.format(format, start) + ", " + String.format(format, end) + ")";
    }

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
