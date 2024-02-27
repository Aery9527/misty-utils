package org.misty.utils.tuple;

import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;

public class IntTupleStats extends TupleStats {

    public static double avg(int[] tuple) {
        return Arrays.stream(tuple).average().orElse(0);
    }

    public static double median(int[] tuple) {
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

    private final int sum;

    private final int max;

    private final int min;

    public IntTupleStats(int[] tuple) {
        super(tuple.length, avg(tuple), median(tuple));

        ToIntFunction<IntSupplier> emptyArrayProcessor = supplier -> tuple.length == 0 ? 0 : supplier.getAsInt();

        this.sum = emptyArrayProcessor.applyAsInt(() -> Arrays.stream(tuple).sum());
        this.max = emptyArrayProcessor.applyAsInt(() -> tuple[tuple.length - 1]);
        this.min = emptyArrayProcessor.applyAsInt(() -> tuple[0]);
    }

    public int getSum() {
        return sum;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

}
