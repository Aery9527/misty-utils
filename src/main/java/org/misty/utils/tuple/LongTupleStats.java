package org.misty.utils.tuple;

import java.util.Arrays;
import java.util.function.LongSupplier;
import java.util.function.ToLongFunction;

public class LongTupleStats extends TupleStats {

    public static double avg(long[] tuple) {
        return Arrays.stream(tuple).average().orElse(0);
    }

    public static double median(long[] tuple) {
        int len = tuple.length;
        if (len == 0) {
            return 0;
        } else if (len % 2 == 0) {
            int middle = len / 2;
            double a = tuple[middle - 1];
            double b = tuple[middle];
            return (a + b) / 2;
        } else {
            return tuple[len / 2];
        }
    }

    private final long sum;

    private final long max;

    private final long min;

    public LongTupleStats(long[] tuple) {
        super(tuple.length, avg(tuple), median(tuple));

        ToLongFunction<LongSupplier> emptyArrayProcessor = supplier -> tuple.length == 0 ? 0 : supplier.getAsLong();

        this.sum = emptyArrayProcessor.applyAsLong(() -> Arrays.stream(tuple).sum());
        this.max = emptyArrayProcessor.applyAsLong(() -> tuple[tuple.length - 1]);
        this.min = emptyArrayProcessor.applyAsLong(() -> tuple[0]);
    }

    public long getSum() {
        return sum;
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

}
