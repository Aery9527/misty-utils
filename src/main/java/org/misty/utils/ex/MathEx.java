package org.misty.utils.ex;

import java.math.BigDecimal;
import java.util.stream.LongStream;

public class MathEx {

    public static long factorial(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("number must be greater than 0");
        }
        return LongStream.rangeClosed(1, number).reduce(1, (a, b) -> a * b);
    }

    public static long getDecimalBit(int value) {
        return (int) Math.log10(value) + 1;
    }

    public static long getDecimalBit(long value) {
        return (int) Math.log10(value) + 1;
    }

    public static float normalizationPercentage(double value) {
        return normalization(value * 100, 2);
    }

    public static float normalizationPercentage(double value, int scale) {
        return normalization(value * 100, scale);
    }

    public static float normalization(double value) {
        return normalization(value, 2);
    }

    public static float normalization(double value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

}
