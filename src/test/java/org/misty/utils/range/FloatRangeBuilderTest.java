package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.stream.Stream;

class FloatRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        FloatRangeBuilder rangeBuilder = Range.floatRangeBuilder()
                .giveLowerBound(-1, 1);

        rangeBuilder.build(-1, Float.MAX_VALUE);
        rangeBuilder.build(1, Float.MAX_VALUE);
        AssertionsEx.assertThrown(() -> rangeBuilder.build(-2, 5))
                .hasMessageContaining("lower")
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeBuilder.build(2, 5))
                .hasMessageContaining("lower")
                .isInstanceOf(IllegalArgumentException.class);

        AssertionsEx.assertThrown(() -> rangeBuilder.giveLowerBound(1, 0))
                .hasMessageContaining("lowerMin")
                .hasMessageContaining("lowerMax")
                .isInstanceOf(IllegalArgumentException.class);

        Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
                .forEach(value -> {
                    AssertionsEx.assertThrown(() -> rangeBuilder.giveLowerBound(value, 0))
                            .hasMessageContaining("lowerMin")
                            .isInstanceOf(IllegalArgumentException.class);
                    AssertionsEx.assertThrown(() -> rangeBuilder.giveLowerBound(0, value))
                            .hasMessageContaining("lowerMax")
                            .isInstanceOf(IllegalArgumentException.class);
                });
    }

    @Test
    public void giveUpperBound() {
        FloatRangeBuilder rangeBuilder = Range.floatRangeBuilder()
                .giveUpperBound(-1, 1);

        rangeBuilder.build(-Float.MAX_VALUE, -1);
        rangeBuilder.build(-Float.MAX_VALUE, 1);
        AssertionsEx.assertThrown(() -> rangeBuilder.build(-5, -2))
                .hasMessageContaining("upper")
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeBuilder.build(-5, 2))
                .hasMessageContaining("upper")
                .isInstanceOf(IllegalArgumentException.class);

        AssertionsEx.assertThrown(() -> rangeBuilder.giveUpperBound(1, 0))
                .hasMessageContaining("upperMin")
                .hasMessageContaining("upperMax")
                .isInstanceOf(IllegalArgumentException.class);

        Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
                .forEach(value -> {
                    AssertionsEx.assertThrown(() -> rangeBuilder.giveUpperBound(value, 0))
                            .hasMessageContaining("upperMin")
                            .isInstanceOf(IllegalArgumentException.class);
                    AssertionsEx.assertThrown(() -> rangeBuilder.giveUpperBound(0, value))
                            .hasMessageContaining("upperMax")
                            .isInstanceOf(IllegalArgumentException.class);
                });
    }

    @Test
    public void build() {
        float lower = -1;
        float upper = 1;

        FloatRange range = Range.floatRangeBuilder().build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);

        FloatRangeBuilder rangeBuilder = Range.floatRangeBuilder();
        Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY)
                .forEach(value -> {
                    AssertionsEx.assertThrown(() -> rangeBuilder.build(value, 0))
                            .hasMessageContaining("lower")
                            .isInstanceOf(IllegalArgumentException.class);
                    AssertionsEx.assertThrown(() -> rangeBuilder.build(0, value))
                            .hasMessageContaining("upper")
                            .isInstanceOf(IllegalArgumentException.class);
                });
    }

}
