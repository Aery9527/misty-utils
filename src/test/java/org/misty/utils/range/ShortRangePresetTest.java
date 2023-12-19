package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class ShortRangePresetTest {

    @Test
    public void random_inRange_outRange() {
        short lower = -1;
        short upper = 1;

        ShortRange range = Range.shortRangeBuilder().build(lower, upper);

        for (short i = 0; i < 1000; i++) {
            short random = range.random();
            AssertionsEx.assertThat(random).isBetween(lower, upper);
            AssertionsEx.assertThat(range.inRange(random)).isTrue();
            AssertionsEx.assertThat(range.outRange(random)).isFalse();
        }
    }

}
