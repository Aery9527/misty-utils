package org.misty.utils.tuple;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.ex.MathEx;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class LongTupleStatsTest {

    @Test
    public void avg_max_min_median() {
        IntConsumer test = length -> {
            int half = length / 2;
            long[] array = IntStream.range(0, length)
                    .mapToLong(i -> MathEx.randomToLong(100))
                    .toArray();

            LongTupleStats tupleStats = TupleStats.of(array);

            long sum = LongStream.of(array).sum();
            double avg = LongStream.of(array).average().orElse(0);
            double median = length == 0 ? 0 : (length % 2 == 1 ? array[half] : (array[half - 1] + array[half]) / 2.0);
            long max = LongStream.of(array).max().orElse(0);
            long min = LongStream.of(array).min().orElse(0);

            Assertions.assertThat(tupleStats.getSize()).isEqualTo(array.length);
            Assertions.assertThat(tupleStats.getAvg()).isEqualTo(avg);
            Assertions.assertThat(tupleStats.getAvg(3)).isEqualTo(MathEx.normalization(avg, 3));
            Assertions.assertThat(tupleStats.getAvgForPercentage(4)).isEqualTo(MathEx.normalizationPercentage(avg, 4));
            Assertions.assertThat(tupleStats.getMedian()).isEqualTo(median);
            Assertions.assertThat(tupleStats.getMedian(3)).isEqualTo(MathEx.normalization(median, 3));
            Assertions.assertThat(tupleStats.getMedianForPercentage(4)).isEqualTo(MathEx.normalizationPercentage(median, 4));
            Assertions.assertThat(tupleStats.getSum()).isEqualTo(sum);
            Assertions.assertThat(tupleStats.getMax()).isEqualTo(max);
            Assertions.assertThat(tupleStats.getMin()).isEqualTo(min);
        };

        test.accept(0);
        test.accept(9);
        test.accept(10);
    }

}
