package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigInteger;

class BigIntegerRangeBuilderTest {

    @Test
    public void giveLowerBound() {
        BigIntegerRangeBuilder rangeBuilder = Range.bigIntegerRangeBuilder()
                .giveLowerBound(-1, 1);

        rangeBuilder.build(BigInteger.valueOf(-1), BigInteger.valueOf(Integer.MAX_VALUE));
        rangeBuilder.build(BigInteger.valueOf(1), BigInteger.valueOf(Integer.MAX_VALUE));
        AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(-2), BigInteger.valueOf(5)))
                .hasMessageContaining("lower")
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(2), BigInteger.valueOf(5)))
                .hasMessageContaining("lower")
                .isInstanceOf(IllegalArgumentException.class);

        AssertionsEx.assertThrown(() -> rangeBuilder.giveLowerBound(1, 0))
                .hasMessageContaining("lowerMin")
                .hasMessageContaining("lowerMax")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveUpperBound() {
        BigIntegerRangeBuilder rangeBuilder = Range.bigIntegerRangeBuilder()
                .giveUpperBound(-1, 1);

        rangeBuilder.build(BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(-1));
        rangeBuilder.build(BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(1));
        AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(-5), BigInteger.valueOf(-2)))
                .hasMessageContaining("upper")
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeBuilder.build(BigInteger.valueOf(-5), BigInteger.valueOf(2)))
                .hasMessageContaining("upper")
                .isInstanceOf(IllegalArgumentException.class);

        AssertionsEx.assertThrown(() -> rangeBuilder.giveUpperBound(1, 0))
                .hasMessageContaining("upperMin")
                .hasMessageContaining("upperMax")
                .isInstanceOf(IllegalArgumentException.class);
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
