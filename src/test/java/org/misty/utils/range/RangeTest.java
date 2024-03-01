package org.misty.utils.range;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class RangeTest {

    @Test
    public void divide_int() {
        Assertions.assertThat(Range.divide(6, 3)).isEqualTo("[4, 6]");
        Assertions.assertThat(Range.divide(5, 3)).isEqualTo("[4, 6]");
        Assertions.assertThat(Range.divide(4, 3)).isEqualTo("[4, 6]");
        Assertions.assertThat(Range.divide(3, 3)).isEqualTo("[1, 3]");
        Assertions.assertThat(Range.divide(2, 3)).isEqualTo("[1, 3]");
        Assertions.assertThat(Range.divide(1, 3)).isEqualTo("[1, 3]");
        Assertions.assertThat(Range.divide(0, 3)).isEqualTo("[0]");
        Assertions.assertThat(Range.divide(-1, 3)).isEqualTo("[-3, -1]");
        Assertions.assertThat(Range.divide(-2, 3)).isEqualTo("[-3, -1]");
        Assertions.assertThat(Range.divide(-3, 3)).isEqualTo("[-3, -1]");
        Assertions.assertThat(Range.divide(-4, 3)).isEqualTo("[-6, -4]");
        Assertions.assertThat(Range.divide(-5, 3)).isEqualTo("[-6, -4]");
        Assertions.assertThat(Range.divide(-6, 3)).isEqualTo("[-6, -4]");

        AssertionsEx.awareThrown(() -> Range.divide(0, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void divide_float() {
        Assertions.assertThat(Range.divide(5.00, 2.5, 2)).isEqualTo("[5.00, 7.50)");
        Assertions.assertThat(Range.divide(3.75, 2.5, 2)).isEqualTo("[2.50, 5.00)");
        Assertions.assertThat(Range.divide(2.50, 2.5, 2)).isEqualTo("[2.50, 5.00)");
        Assertions.assertThat(Range.divide(1.25, 2.5, 2)).isEqualTo("[0.00, 2.50)");
        Assertions.assertThat(Range.divide(0.00, 2.5, 2)).isEqualTo("[0.00, 2.50)");
        Assertions.assertThat(Range.divide(-1.25, 2.5, 2)).isEqualTo("[-2.50, 0.00)");
        Assertions.assertThat(Range.divide(-2.50, 2.5, 2)).isEqualTo("[-2.50, 0.00)");
        Assertions.assertThat(Range.divide(-3.75, 2.5, 2)).isEqualTo("[-5.00, -2.50)");
        Assertions.assertThat(Range.divide(-5.00, 2.5, 2)).isEqualTo("[-5.00, -2.50)");

        AssertionsEx.awareThrown(() -> Range.divide(0, 0, 2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void divide_double() {

    }

}
