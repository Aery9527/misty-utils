package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MathExTest {

    @Test
    public void factorial() {
        Assertions.assertThat(MathEx.factorial(5)).isEqualTo(5 * 4 * 3 * 2);
        Assertions.assertThat(MathEx.factorial(9)).isEqualTo(9 * 8 * 7 * 6 * 5 * 4 * 3 * 2);
    }

}
