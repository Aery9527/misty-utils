package org.misty.utils.ex;

import java.util.stream.LongStream;

public class MathEx {

    public static long factorial(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("number must be greater than 0");
        }
        return LongStream.rangeClosed(1, number).reduce(1, (a, b) -> a * b);
    }

}
