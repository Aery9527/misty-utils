package org.misty.utils.tuple;

import org.misty.utils.ex.MathEx;

import java.util.Arrays;

public abstract class TupleStats {

    public static IntTupleStats of(int[] tuple) {
        return of(tuple, true);
    }

    public static IntTupleStats of(int[] tuple, boolean sort) {
        if (sort) {
            Arrays.sort(tuple);
        }

        return new IntTupleStats(tuple);
    }

    public static LongTupleStats of(long[] tuple) {
        return of(tuple, true);
    }

    public static LongTupleStats of(long[] tuple, boolean sort) {
        if (sort) {
            Arrays.sort(tuple);
        }

        return new LongTupleStats(tuple);
    }

    public static DoubleTupleStats of(double[] tuple) {
        return of(tuple, true);
    }

    public static DoubleTupleStats of(double[] tuple, boolean sort) {
        if (sort) {
            Arrays.sort(tuple);
        }

        return new DoubleTupleStats(tuple);
    }

    private final double avg;

    private final double median;

    protected TupleStats(double avg, double median) {
        this.avg = avg;
        this.median = median;
    }

    public double getAvg() {
        return avg;
    }

    public double getAvg(int scale) {
        return MathEx.normalization(this.avg, scale);
    }

    public double getAvgForPercentage(int scale) {
        return MathEx.normalizationPercentage(this.avg, scale);
    }

    public double getMedian() {
        return median;
    }

    public double getMedian(int scale) {
        return MathEx.normalization(this.median, scale);
    }

    public double getMedianForPercentage(int scale) {
        return MathEx.normalizationPercentage(this.median, scale);
    }

}
