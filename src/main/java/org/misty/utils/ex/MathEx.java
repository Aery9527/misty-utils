package org.misty.utils.ex;

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

}
