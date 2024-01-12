package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

class FloatRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BiConsumer<String, FloatRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveLowerBound(-1, 1);

            rangeBuilder.build(-1, Float.MAX_VALUE);
            rangeBuilder.build(1, Float.MAX_VALUE);
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

            Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
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

        test.accept("", Range.floatRangeBuilder());
        test.accept("kerker", Range.floatRangeBuilder("kerker"));
    }

    @Test
    public void giveUpperBound() {
        BiConsumer<String, FloatRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveUpperBound(-1, 1);

            rangeBuilder.build(-Float.MAX_VALUE, -1);
            rangeBuilder.build(-Float.MAX_VALUE, 1);
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

            Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
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

        test.accept("", Range.floatRangeBuilder());
        test.accept("kerker", Range.floatRangeBuilder("kerker"));
    }

    @Test
    public void build() {
        float lower = -1;
        float upper = 1;

        FloatRange range = Range.floatRangeBuilder().build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);

        BiConsumer<String, FloatRangeBuilder> test = (title, rangeBuilder) -> {
            Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
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

        test.accept("", Range.floatRangeBuilder());
        test.accept("kerker", Range.floatRangeBuilder("kerker"));
    }

}
