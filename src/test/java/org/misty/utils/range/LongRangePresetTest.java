package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class LongRangePresetTest {

    @Test
    public void random_inRange_outRange() {
        long lower = -1;
        long upper = 1;

        LongRange range = Range.longRangeBuilder().build(lower, upper);

        for (int i = 0; i < 1000; i++) {
            long random = range.random();
            AssertionsEx.assertThat(random).isBetween(lower, upper);
            AssertionsEx.assertThat(range.inRange(random)).isTrue();
            AssertionsEx.assertThat(range.outRange(random)).isFalse();
        }
    }

}
