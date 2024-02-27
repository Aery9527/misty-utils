package org.misty.utils.tuple;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.ex.MathEx;

import java.util.function.IntConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class DoubleTupleStatsTest {

    @Test
    public void avg_max_min_median() {
        IntConsumer test = length -> {
            int half = length / 2;
            double[] array = IntStream.range(0, length)
                    .mapToDouble(i -> MathEx.random(100))
                    .toArray();

            DoubleTupleStats tupleStats = TupleStats.of(array);

            double sum = DoubleStream.of(array).sum();
            double avg = DoubleStream.of(array).average().orElse(0);
            double median = length == 0 ? 0 : (length % 2 == 1 ? array[half] : (array[half - 1] + array[half]) / 2.0);
            double max = DoubleStream.of(array).max().orElse(0);
            double min = DoubleStream.of(array).min().orElse(0);

            Assertions.assertThat(tupleStats.getSize()).isEqualTo(array.length);
            Assertions.assertThat(tupleStats.getAvg()).isEqualTo(avg);
            Assertions.assertThat(tupleStats.getAvg(3)).isEqualTo(MathEx.normalization(avg, 3));
            Assertions.assertThat(tupleStats.getAvgForPercentage(4)).isEqualTo(MathEx.normalizationPercentage(avg, 4));
            Assertions.assertThat(tupleStats.getMedian()).isEqualTo(median);
            Assertions.assertThat(tupleStats.getMedian(3)).isEqualTo(MathEx.normalization(median, 3));
            Assertions.assertThat(tupleStats.getMedianForPercentage(4)).isEqualTo(MathEx.normalizationPercentage(median, 4));
            Assertions.assertThat(tupleStats.getSum()).isEqualTo(sum);
            Assertions.assertThat(tupleStats.getSum(3)).isEqualTo(MathEx.normalization(sum, 3));
            Assertions.assertThat(tupleStats.getSumForPercentage(4)).isEqualTo(MathEx.normalizationPercentage(sum, 4));
            Assertions.assertThat(tupleStats.getMax()).isEqualTo(max);
            Assertions.assertThat(tupleStats.getMax(3)).isEqualTo(MathEx.normalization(max, 3));
            Assertions.assertThat(tupleStats.getMaxForPercentage(4)).isEqualTo(MathEx.normalizationPercentage(max, 4));
            Assertions.assertThat(tupleStats.getMin()).isEqualTo(min);
            Assertions.assertThat(tupleStats.getMin(3)).isEqualTo(MathEx.normalization(min, 3));
            Assertions.assertThat(tupleStats.getMinForPercentage(4)).isEqualTo(MathEx.normalizationPercentage(min, 4));
        };

        test.accept(0);
        test.accept(9);
        test.accept(10);
    }

}
