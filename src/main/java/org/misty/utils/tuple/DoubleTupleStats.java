package org.misty.utils.tuple;

import java.util.Arrays;

public class DoubleTupleStats extends TupleStats {

    public static double avg(double[] tuple) {
        return Arrays.stream(tuple).average().orElse(0);
    }

    public static double median(double[] tuple) {
        int len = tuple.length;
        if (len % 2 == 0) {
            int middle = len / 2;
            double a = tuple[middle - 1];
            double b = tuple[middle];
            return (a + b) / 2;
        } else {
            return tuple[len / 2];
        }
    }

    private final double max;

    private final double min;

    public DoubleTupleStats(double[] tuple) {
        super(avg(tuple), median(tuple));

        this.max = tuple[tuple.length - 1];
        this.min = tuple[0];
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

}
