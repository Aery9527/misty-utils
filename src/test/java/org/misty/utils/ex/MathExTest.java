package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathExTest {

    @Test
    public void factorial() {
        Assertions.assertThat(MathEx.factorial(5)).isEqualTo(5 * 4 * 3 * 2);
        Assertions.assertThat(MathEx.factorial(9)).isEqualTo(9 * 8 * 7 * 6 * 5 * 4 * 3 * 2);
    }

    @Test
    public void getDecimalBit() {
        Assertions.assertThat(MathEx.getDecimalBit(123)).isEqualTo(3);
        Assertions.assertThat(MathEx.getDecimalBit(123456789)).isEqualTo(9);
        Assertions.assertThat(MathEx.getDecimalBit(123L)).isEqualTo(3);
        Assertions.assertThat(MathEx.getDecimalBit(123456789L)).isEqualTo(9);
    }

    @Test
    public void normalization() {
        double value = 123.456789;
        Assertions.assertThat(MathEx.normalization(value)).isEqualTo(123.46);
        Assertions.assertThat(MathEx.normalization(value, 3)).isEqualTo(123.457);
    }

    @Test
    public void normalizationPercentage() {
        double value = 123.456789;
        Assertions.assertThat(MathEx.normalizationPercentage(value)).isEqualTo(12345.68);
        Assertions.assertThat(MathEx.normalizationPercentage(value, 3)).isEqualTo(12345.679);
    }

    @Test
    public void random() {
        for (int i = 0; i < 100; i++) {
            double ceiling = 100;
            double floor = 50;
            Assertions.assertThat(MathEx.random(ceiling)).isBetween(0d, ceiling);
            Assertions.assertThat(MathEx.random(floor, ceiling)).isBetween(floor, ceiling);
        }
    }

    @Test
    public void randomToInt() {
        for (int i = 0; i < 100; i++) {
            int ceiling = 100;
            int floor = 50;
            Assertions.assertThat(MathEx.randomToInt(ceiling)).isBetween(0, ceiling);
            Assertions.assertThat(MathEx.randomToInt(floor, ceiling)).isBetween(floor, ceiling);
        }
    }

    @Test
    public void randomToLong() {
        for (int i = 0; i < 100; i++) {
            long ceiling = 100;
            long floor = 50;
            Assertions.assertThat(MathEx.randomToLong(ceiling)).isBetween(0L, ceiling);
            Assertions.assertThat(MathEx.randomToLong(floor, ceiling)).isBetween(floor, ceiling);
        }
    }

}
