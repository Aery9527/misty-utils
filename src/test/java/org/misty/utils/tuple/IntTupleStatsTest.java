package org.misty.utils.tuple;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty.utils.ex.MathEx;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class IntTupleStatsTest {

    @Test
    public void avg_max_min_median() {
        IntConsumer test = length -> {
            int half = length / 2;
            int[] array = IntStream.range(0, length)
                    .map(i -> MathEx.randomToInt(100))
                    .toArray();

            IntTupleStats intTupleStats = TupleStats.of(array);

            double avg = IntStream.of(array).average().getAsDouble();
            double median = length % 2 == 1 ? array[half] : (array[half - 1] + array[half]) / 2.0;
            int max = IntStream.of(array).max().getAsInt();
            int min = IntStream.of(array).min().getAsInt();

            Assertions.assertThat(intTupleStats.getAvg()).isEqualTo(avg);
            Assertions.assertThat(intTupleStats.getMedian()).isEqualTo(median);
            Assertions.assertThat(intTupleStats.getMax()).isEqualTo(max);
            Assertions.assertThat(intTupleStats.getMin()).isEqualTo(min);
        };

        test.accept(9);
        test.accept(10);
    }

}
