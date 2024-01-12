package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.BiConsumer;

class LongRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BiConsumer<String, LongRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveLowerBound(-1, 1);

            rangeBuilder.build(-1, Long.MAX_VALUE);
            rangeBuilder.build(1, Long.MAX_VALUE);
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
        };

        test.accept("", Range.longRangeBuilder());
        test.accept("kerker", Range.longRangeBuilder("kerker"));
    }

    @Test
    public void giveUpperBound() {
        BiConsumer<String, LongRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveUpperBound(-1, 1);

            rangeBuilder.build(Long.MIN_VALUE, -1);
            rangeBuilder.build(Long.MIN_VALUE, 1);
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
        };

        test.accept("", Range.longRangeBuilder());
        test.accept("kerker", Range.longRangeBuilder("kerker"));
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
