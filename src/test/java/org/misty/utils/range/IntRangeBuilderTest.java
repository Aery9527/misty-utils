package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.BiConsumer;

class IntRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BiConsumer<String, IntRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveLowerBound(-1, 1);

            rangeBuilder.build(-1, Integer.MAX_VALUE);
            rangeBuilder.build(1, Integer.MAX_VALUE);
            AssertionsEx.assertThrown(() -> rangeBuilder.build(-2, 5))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.assertThrown(() -> rangeBuilder.build(2, 5))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.assertThrown(() -> rangeBuilder.giveLowerBound(1, 0))
                    .hasMessageContaining("lowerMin")
                    .hasMessageContaining("lowerMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.intRangeBuilder());
        test.accept("kerker", Range.intRangeBuilder("kerker"));
    }

    @Test
    public void giveUpperBound() {
        BiConsumer<String, IntRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveUpperBound(-1, 1);

            rangeBuilder.build(Integer.MIN_VALUE, -1);
            rangeBuilder.build(Integer.MIN_VALUE, 1);
            AssertionsEx.assertThrown(() -> rangeBuilder.build(-5, -2))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.assertThrown(() -> rangeBuilder.build(-5, 2))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.assertThrown(() -> rangeBuilder.giveUpperBound(1, 0))
                    .hasMessageContaining("upperMin")
                    .hasMessageContaining("upperMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.intRangeBuilder());
        test.accept("kerker", Range.intRangeBuilder("kerker"));
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
