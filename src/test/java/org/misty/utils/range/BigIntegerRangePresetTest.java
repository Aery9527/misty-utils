package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

class BigIntegerRangePresetTest {

    @Test
    public void random_inRange_outRange() {
        int times = 100_000;

        BiConsumer<BigInteger, BigInteger> test = (lower, upper) -> {
            BigIntegerRange range = Range.bigIntegerRangeBuilder().build(lower, upper);
            System.out.println(((BigIntegerRangePreset) range).getGapRandom());

            Map<BigInteger, AtomicInteger> count = new HashMap<>();

            for (int i = 0; i < times; i++) {
                BigInteger random = range.random();
                AssertionsEx.assertThat(random).isBetween(lower, upper);
                AssertionsEx.assertThat(range.inRange(random)).isTrue();
                AssertionsEx.assertThat(range.outRange(random)).isFalse();

                count.computeIfAbsent(random, key -> new AtomicInteger()).incrementAndGet();
            }

            count.forEach((key, value) -> { //  this issue please see BreakDown2PowerRandom in BigIntegerRangePreset
                System.out.println(String.format("%6d", key) + " appear " +
                        String.format("%6d", value.get()) + " times " +
                        String.format("%7.3f", ((double) value.get()) / times * 100.0) + "%");
            });

            System.out.println();
        };

//        test.accept(BigInteger.valueOf(0), BigInteger.valueOf(1));
//        test.accept(BigInteger.valueOf(0), BigInteger.valueOf(2));
//        test.accept(BigInteger.valueOf(0), BigInteger.valueOf(3));
        test.accept(BigInteger.valueOf(0), BigInteger.valueOf(15));
//        test.accept(BigInteger.valueOf(-2), BigInteger.valueOf(2));
//        test.accept(BigInteger.valueOf(-2), BigInteger.valueOf(3));
//        test.accept(new BigInteger("-8940651051065784026"), new BigInteger("-8940651051065784022")); // gap 4
//        test.accept(new BigInteger("8940651051065784026"), new BigInteger("8940651051065784030")); // gap 4
    }

    @Test
    void getGapRandom() {
        BigInteger lower = BigInteger.valueOf(-1);
        BigInteger gap = BigInteger.valueOf(7);
        BigInteger upper = lower.add(gap);

        BigIntegerRangePreset range = (BigIntegerRangePreset) Range.bigIntegerRangeBuilder().build(lower, upper);
        System.out.println(range.getGapRandom());
    }

}
