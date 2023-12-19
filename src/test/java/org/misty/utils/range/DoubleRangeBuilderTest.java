package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.stream.Stream;

class DoubleRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        DoubleRangeBuilder rangeBuilder = Range.doubleRangeBuilder()
                .giveLowerBound(-1, 1);

        rangeBuilder.build(-1, Double.MAX_VALUE);
        rangeBuilder.build(1, Double.MAX_VALUE);
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

        Stream.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
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
        DoubleRangeBuilder rangeBuilder = Range.doubleRangeBuilder()
                .giveUpperBound(-1, 1);

        rangeBuilder.build(-Double.MAX_VALUE, -1);
        rangeBuilder.build(-Double.MAX_VALUE, 1);
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

        Stream.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
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
        double lower = -1;
        double upper = 1;

        DoubleRange range = Range.doubleRangeBuilder().build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);

        DoubleRangeBuilder rangeBuilder = Range.doubleRangeBuilder();
        Stream.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
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
