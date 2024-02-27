package org.misty.utils.tuple;

import org.misty.utils.ex.MathEx;

import java.util.Arrays;
import java.util.function.DoubleSupplier;
import java.util.function.ToDoubleFunction;

public class DoubleTupleStats extends TupleStats {

    public static double avg(double[] tuple) {
        return Arrays.stream(tuple).average().orElse(0);
    }

    public static double median(double[] tuple) {
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

    private final double sum;

    private final double max;

    private final double min;

    public DoubleTupleStats(double[] tuple) {
        super(tuple.length, avg(tuple), median(tuple));

        ToDoubleFunction<DoubleSupplier> emptyArrayProcessor = supplier -> tuple.length == 0 ? 0 : supplier.getAsDouble();

        this.sum = emptyArrayProcessor.applyAsDouble(() -> Arrays.stream(tuple).sum());
        this.max = emptyArrayProcessor.applyAsDouble(() -> tuple[tuple.length - 1]);
        this.min = emptyArrayProcessor.applyAsDouble(() -> tuple[0]);
    }

    public double getSum() {
        return sum;
    }

    public double getSum(int scale) {
        return MathEx.normalization(this.sum, scale);
    }

    public double getSumForPercentage(int scale) {
        return MathEx.normalizationPercentage(this.sum, scale);
    }

    public double getMax() {
        return max;
    }

    public double getMax(int scale) {
        return MathEx.normalization(this.max, scale);
    }

    public double getMaxForPercentage(int scale) {
        return MathEx.normalizationPercentage(this.max, scale);
    }

    public double getMin() {
        return min;
    }

    public double getMin(int scale) {
        return MathEx.normalization(this.min, scale);
    }

    public double getMinForPercentage(int scale) {
        return MathEx.normalizationPercentage(this.min, scale);
    }

}
