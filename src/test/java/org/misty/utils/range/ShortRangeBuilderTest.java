package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.BiConsumer;

public class ShortRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BiConsumer<String, ShortRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveLowerBound((short) -1, (short) 1);

            rangeBuilder.build((short) -1, Short.MAX_VALUE);
            rangeBuilder.build((short) 1, Short.MAX_VALUE);
            AssertionsEx.awareThrown(() -> rangeBuilder.build((short) -2, (short) 5))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeBuilder.build((short) 2, (short) 5))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.awareThrown(() -> rangeBuilder.giveLowerBound((short) 1, (short) 0))
                    .hasMessageContaining("lowerMin")
                    .hasMessageContaining("lowerMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.shortRangeBuilder());
        test.accept("kerker", Range.shortRangeBuilder("kerker"));
    }

    @Test
    public void giveUpperBound() {
        BiConsumer<String, ShortRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveUpperBound((short) -1, (short) 1);

            rangeBuilder.build(Short.MIN_VALUE, (short) -1);
            rangeBuilder.build(Short.MIN_VALUE, (short) 1);
            AssertionsEx.awareThrown(() -> rangeBuilder.build((short) -5, (short) -2))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeBuilder.build((short) -5, (short) 2))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.awareThrown(() -> rangeBuilder.giveUpperBound((short) 1, (short) 0))
                    .hasMessageContaining("upperMin")
                    .hasMessageContaining("upperMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.shortRangeBuilder());
        test.accept("kerker", Range.shortRangeBuilder("kerker"));
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
