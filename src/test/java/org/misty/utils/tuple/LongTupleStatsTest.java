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

            LongTupleStats intTupleStats = TupleStats.of(array);

            double avg = LongStream.of(array).average().getAsDouble();
            double median = length % 2 == 1 ? array[half] : (array[half - 1] + array[half]) / 2.0;
            long max = LongStream.of(array).max().getAsLong();
            long min = LongStream.of(array).min().getAsLong();

            Assertions.assertThat(intTupleStats.getAvg()).isEqualTo(avg);
            Assertions.assertThat(intTupleStats.getMedian()).isEqualTo(median);
            Assertions.assertThat(intTupleStats.getMax()).isEqualTo(max);
            Assertions.assertThat(intTupleStats.getMin()).isEqualTo(min);
        };

        test.accept(9);
        test.accept(10);
    }

}
