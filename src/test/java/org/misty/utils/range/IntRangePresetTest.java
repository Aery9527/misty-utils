package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class IntRangePresetTest {

    @Test
    public void random_inRange_outRange() {
        int lower = -1;
        int upper = 1;

        IntRange range = Range.intRangeBuilder().build(lower, upper);

        for (int i = 0; i < 1000; i++) {
            int random = range.random();
            AssertionsEx.assertThat(random).isBetween(lower, upper);
            AssertionsEx.assertThat(range.inRange(random)).isTrue();
            AssertionsEx.assertThat(range.outRange(random)).isFalse();
        }
    }

}
