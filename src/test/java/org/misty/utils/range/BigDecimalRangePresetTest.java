package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;

class BigDecimalRangePresetTest {

    @Test
    public void random_inRange_outRange() {
        BigDecimal lower = BigDecimal.valueOf(-1);
        BigDecimal upper = BigDecimal.valueOf(1);

        BigDecimalRange range = Range.bigDecimalRangeBuilder().build(lower, upper);

        for (int i = 0; i < 1000; i++) {
            BigDecimal random = range.random();
            AssertionsEx.assertThat(random).isBetween(lower, upper);
            AssertionsEx.assertThat(range.inRange(random)).isTrue();
            AssertionsEx.assertThat(range.outRange(random)).isFalse();
        }
    }

}
