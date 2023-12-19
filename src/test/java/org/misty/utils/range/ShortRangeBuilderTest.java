package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class ShortRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        ShortRangeBuilder rangeBuilder = Range.shortRangeBuilder()
                .giveLowerBound((short) -1, (short) 1);

        rangeBuilder.build((short) -1, Short.MAX_VALUE);
        rangeBuilder.build((short) 1, Short.MAX_VALUE);
        AssertionsEx.assertThrown(() -> rangeBuilder.build((short) -2, (short) 5))
                .hasMessageContaining("lower")
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeBuilder.build((short) 2, (short) 5))
                .hasMessageContaining("lower")
                .isInstanceOf(IllegalArgumentException.class);

        AssertionsEx.assertThrown(() -> rangeBuilder.giveLowerBound((short) 1, (short) 0))
                .hasMessageContaining("lowerMin")
                .hasMessageContaining("lowerMax")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveUpperBound() {
        ShortRangeBuilder rangeBuilder = Range.shortRangeBuilder()
                .giveUpperBound((short) -1, (short) 1);

        rangeBuilder.build(Short.MIN_VALUE, (short) -1);
        rangeBuilder.build(Short.MIN_VALUE, (short) 1);
        AssertionsEx.assertThrown(() -> rangeBuilder.build((short) -5, (short) -2))
                .hasMessageContaining("upper")
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeBuilder.build((short) -5, (short) 2))
                .hasMessageContaining("upper")
                .isInstanceOf(IllegalArgumentException.class);

        AssertionsEx.assertThrown(() -> rangeBuilder.giveUpperBound((short) 1, (short) 0))
                .hasMessageContaining("upperMin")
                .hasMessageContaining("upperMax")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void build() {
        short lower = -1;
        short upper = 1;

        ShortRange range = Range.shortRangeBuilder().build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);
    }

}
