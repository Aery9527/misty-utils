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

            DoubleTupleStats intTupleStats = TupleStats.of(array);

            double avg = DoubleStream.of(array).average().getAsDouble();
            double median = length % 2 == 1 ? array[half] : (array[half - 1] + array[half]) / 2.0;
            double max = DoubleStream.of(array).max().getAsDouble();
            double min = DoubleStream.of(array).min().getAsDouble();

            Assertions.assertThat(intTupleStats.getAvg()).isEqualTo(avg);
            Assertions.assertThat(intTupleStats.getMedian()).isEqualTo(median);
            Assertions.assertThat(intTupleStats.getMax()).isEqualTo(max);
            Assertions.assertThat(intTupleStats.getMin()).isEqualTo(min);
        };

        test.accept(9);
        test.accept(10);
    }

}
