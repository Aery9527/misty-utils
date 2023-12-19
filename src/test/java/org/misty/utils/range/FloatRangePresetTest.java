package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class FloatRangePresetTest {

    @Test
    public void random_inRange_outRange() {
        float lower = -1;
        float upper = 1;

        FloatRange range = Range.floatRangeBuilder().build(lower, upper);

        for (int i = 0; i < 1000; i++) {
            float random = range.random();
            AssertionsEx.assertThat(random).isBetween(lower, upper);
            AssertionsEx.assertThat(range.inRange(random)).isTrue();
            AssertionsEx.assertThat(range.outRange(random)).isFalse();
        }
    }

}
