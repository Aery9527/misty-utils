package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class LongRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        LongRangeBuilder rangeBuilder = Range.longRangeBuilder()
                .giveLowerBound(-1, 1);

        rangeBuilder.build(-1, Long.MAX_VALUE);
        rangeBuilder.build(1, Long.MAX_VALUE);
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
        LongRangeBuilder rangeBuilder = Range.longRangeBuilder()
                .giveUpperBound(-1, 1);

        rangeBuilder.build(Long.MIN_VALUE, -1);
        rangeBuilder.build(Long.MIN_VALUE, 1);
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
        long lower = -1;
        long upper = 1;

        LongRange range = Range.longRangeBuilder().build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);
    }

}
