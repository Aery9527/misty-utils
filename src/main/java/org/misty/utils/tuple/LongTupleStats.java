package org.misty.utils.tuple;

import java.util.Arrays;

public class LongTupleStats extends TupleStats {

    public static double avg(long[] tuple) {
        return Arrays.stream(tuple).average().orElse(0);
    }

    public static double median(long[] tuple) {
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

    private final long max;

    private final long min;

    public LongTupleStats(long[] tuple) {
        super(avg(tuple), median(tuple));

        this.max = tuple[tuple.length - 1];
        this.min = tuple[0];
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

}
