package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiConsumer;

public class BigDecimalRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BiConsumer<String, BigDecimalRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveLowerBound(-1, 1);

            rangeBuilder.build(BigDecimal.valueOf(-1), BigDecimal.valueOf(Integer.MAX_VALUE));
            rangeBuilder.build(BigDecimal.valueOf(1), BigDecimal.valueOf(Integer.MAX_VALUE));
            AssertionsEx.awareThrown(() -> rangeBuilder.build(BigDecimal.valueOf(-2), BigDecimal.valueOf(5)))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeBuilder.build(BigDecimal.valueOf(2), BigDecimal.valueOf(5)))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.awareThrown(() -> rangeBuilder.giveLowerBound(1, 0))
                    .hasMessageContaining("lowerMin")
                    .hasMessageContaining("lowerMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.bigDecimalRangeBuilder());
        test.accept("kerker", Range.bigDecimalRangeBuilder("kerker"));
    }

    @Test
    public void giveUpperBound() {
        BiConsumer<String, BigDecimalRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveUpperBound(-1, 1);

            rangeBuilder.build(BigDecimal.valueOf(Integer.MIN_VALUE), BigDecimal.valueOf(-1));
            rangeBuilder.build(BigDecimal.valueOf(Integer.MIN_VALUE), BigDecimal.valueOf(1));
            AssertionsEx.awareThrown(() -> rangeBuilder.build(BigDecimal.valueOf(-5), BigDecimal.valueOf(-2)))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeBuilder.build(BigDecimal.valueOf(-5), BigDecimal.valueOf(2)))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.awareThrown(() -> rangeBuilder.giveUpperBound(1, 0))
                    .hasMessageContaining("upperMin")
                    .hasMessageContaining("upperMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.bigDecimalRangeBuilder());
        test.accept("kerker", Range.bigDecimalRangeBuilder("kerker"));
    }

    @Test
    public void build() {
        int scale = 4;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        BigDecimal lower = BigDecimal.valueOf(-1);
        BigDecimal upper = BigDecimal.valueOf(1);

        BigDecimalRange range = Range.bigDecimalRangeBuilder()
                .giveScale(scale, roundingMode)
                .build(lower, upper);

        AssertionsEx.assertThat(range.getScale()).isEqualTo(scale);
        AssertionsEx.assertThat(range.getRoundingMode()).isEqualTo(roundingMode);
        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower.setScale(scale, roundingMode));
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper.setScale(scale, roundingMode));
    }

}
