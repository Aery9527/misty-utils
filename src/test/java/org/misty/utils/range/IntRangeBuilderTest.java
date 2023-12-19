package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class IntRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        IntRangeBuilder rangeBuilder = Range.intRangeBuilder()
                .giveLowerBound(-1, 1);

        rangeBuilder.build(-1, Integer.MAX_VALUE);
        rangeBuilder.build(1, Integer.MAX_VALUE);
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
    }

    @Test
    public void giveUpperBound() {
        IntRangeBuilder rangeBuilder = Range.intRangeBuilder()
                .giveUpperBound(-1, 1);

        rangeBuilder.build(Integer.MIN_VALUE, -1);
        rangeBuilder.build(Integer.MIN_VALUE, 1);
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
    }

    @Test
    public void build() {
        int lower = -1;
        int upper = 1;

        IntRange range = Range.intRangeBuilder().build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);
    }

}
