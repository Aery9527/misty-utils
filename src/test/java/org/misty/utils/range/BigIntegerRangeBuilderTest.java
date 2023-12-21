package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigInteger;
import java.util.function.BiConsumer;

class BigIntegerRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BiConsumer<String, BigIntegerRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveLowerBound(-1, 1);

            rangeBuilder.build(BigInteger.valueOf(-1), BigInteger.valueOf(Integer.MAX_VALUE));
            rangeBuilder.build(BigInteger.valueOf(1), BigInteger.valueOf(Integer.MAX_VALUE));
            AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(-2), BigInteger.valueOf(5)))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(2), BigInteger.valueOf(5)))
                    .hasMessageContaining("lower")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.assertThrown(() -> rangeBuilder.giveLowerBound(1, 0))
                    .hasMessageContaining("lowerMin")
                    .hasMessageContaining("lowerMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.bigIntegerRangeBuilder());
        test.accept("kerker", Range.bigIntegerRangeBuilder("kerker"));
    }

    @Test
    public void giveUpperBound() {
        BiConsumer<String, BigIntegerRangeBuilder> test = (title, rangeBuilder) -> {
            rangeBuilder.giveUpperBound(-1, 1);

            rangeBuilder.build(BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(-1));
            rangeBuilder.build(BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(1));
            AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(-5), BigInteger.valueOf(-2)))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(-5), BigInteger.valueOf(2)))
                    .hasMessageContaining("upper")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            AssertionsEx.assertThrown(() -> rangeBuilder.giveUpperBound(1, 0))
                    .hasMessageContaining("upperMin")
                    .hasMessageContaining("upperMax")
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
        };

        test.accept("", Range.bigIntegerRangeBuilder());
        test.accept("kerker", Range.bigIntegerRangeBuilder("kerker"));
    }

    @Test
    public void build() {
        BigInteger lower = BigInteger.valueOf(-1);
        BigInteger upper = BigInteger.valueOf(1);

        BigIntegerRange range = Range.bigIntegerRangeBuilder()
                .build(lower, upper);

        AssertionsEx.assertThat(range.getLower()).isEqualTo(lower);
        AssertionsEx.assertThat(range.getUpper()).isEqualTo(upper);
    }

}
