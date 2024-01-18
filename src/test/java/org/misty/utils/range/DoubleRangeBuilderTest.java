package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class DoubleRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BiConsumer<String, DoubleRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveLowerBound(-1, 1);

            rangeBuilder.build(-1, Double.MAX_VALUE);
            rangeBuilder.build(1, Double.MAX_VALUE);
            AssertionsEx.awareThrown(() -> rangeBuilder.build(-2, 5))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeBuilder.build(2, 5))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.awareThrown(() -> rangeBuilder.giveLowerBound(1, 0))
                    .hasMessageContaining("lowerMin")
                    .hasMessageContaining("lowerMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            Stream.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
                    .forEach(value -> {
                        AssertionsEx.awareThrown(() -> rangeBuilder.giveLowerBound(value, 0))
                                .hasMessageContaining("lowerMin")
                                .hasMessageContaining(title)
                                .isInstanceOf(IllegalArgumentException.class);
                        AssertionsEx.awareThrown(() -> rangeBuilder.giveLowerBound(0, value))
                                .hasMessageContaining("lowerMax")
                                .hasMessageContaining(title)
                                .isInstanceOf(IllegalArgumentException.class);
                    });
        };

        test.accept("", Range.doubleRangeBuilder());
        test.accept("kerker", Range.doubleRangeBuilder("kerker"));
    }

    @Test
    public void giveUpperBound() {
        BiConsumer<String, DoubleRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveUpperBound(-1, 1);

            rangeBuilder.build(-Double.MAX_VALUE, -1);
            rangeBuilder.build(-Double.MAX_VALUE, 1);
            AssertionsEx.awareThrown(() -> rangeBuilder.build(-5, -2))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeBuilder.build(-5, 2))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.awareThrown(() -> rangeBuilder.giveUpperBound(1, 0))
                    .hasMessageContaining("upperMin")
                    .hasMessageContaining("upperMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            Stream.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
                    .forEach(value -> {
                        AssertionsEx.awareThrown(() -> rangeBuilder.giveUpperBound(value, 0))
                                .hasMessageContaining("upperMin")
                                .hasMessageContaining(title)
                                .isInstanceOf(IllegalArgumentException.class);
                        AssertionsEx.awareThrown(() -> rangeBuilder.giveUpperBound(0, value))
                                .hasMessageContaining("upperMax")
                                .hasMessageContaining(title)
                                .isInstanceOf(IllegalArgumentException.class);
                    });
        };

        test.accept("", Range.doubleRangeBuilder());
        test.accept("kerker", Range.doubleRangeBuilder("kerker"));
    }

    @Test
    public void build() {
        double lower = -1;
        double upper = 1;

        DoubleRange range = Range.doubleRangeBuilder().build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);

        BiConsumer<String, DoubleRangeBuilder> test = (title, rangeBuilder) -> {
            Stream.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
                    .forEach(value -> {
                        AssertionsEx.awareThrown(() -> rangeBuilder.build(value, 0))
                                .hasMessageContaining("lower")
                                .hasMessageContaining(title)
                                .isInstanceOf(IllegalArgumentException.class);
                        AssertionsEx.awareThrown(() -> rangeBuilder.build(0, value))
                                .hasMessageContaining("upper")
                                .hasMessageContaining(title)
                                .isInstanceOf(IllegalArgumentException.class);
                    });
        };

        test.accept("", Range.doubleRangeBuilder());
        test.accept("kerker", Range.doubleRangeBuilder("kerker"));
    }

}
