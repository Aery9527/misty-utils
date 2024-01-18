package org.misty.utils.range;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class DoubleRangePresetTest {

    @Test
    public void random_inRange_outRange() {
        double lower = -1;
        double upper = 1;

        DoubleRange range = Range.doubleRangeBuilder().build(lower, upper);

        for (int i = 0; i < 1000; i++) {
            double random = range.random();
            AssertionsEx.assertThat(random).isBetween(lower, upper);
            AssertionsEx.assertThat(range.inRange(random)).isTrue();
            AssertionsEx.assertThat(range.outRange(random)).isFalse();
        }
    }

}
