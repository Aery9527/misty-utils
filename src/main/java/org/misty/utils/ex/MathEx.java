package org.misty.utils.ex;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static double normalizationPercentage(double value) {
        return normalization(value * 100, 2);
    }

    public static double normalizationPercentage(double value, int scale) {
        return normalization(value * 100, scale);
    }

    public static double normalization(double value) {
        return normalization(value, 2);
    }

    public static double normalization(double value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static double random(double ceiling) {
        return random(0, ceiling);
    }

    public static double random(double floor, double ceiling) {
        return Math.random() * (ceiling - floor) + floor;
    }

    public static int randomToInt(int ceiling) {
        return randomToInt(0, ceiling);
    }

    public static int randomToInt(int floor, int ceiling) {
        return (int) (Math.random() * (ceiling - floor + 1) + floor);
    }

    public static long randomToLong(long ceiling) {
        return randomToLong(0, ceiling);
    }

    public static long randomToLong(long floor, long ceiling) {
        return (long) (Math.random() * (ceiling - floor + 1) + floor);
    }

}
