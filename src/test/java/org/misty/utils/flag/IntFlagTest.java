package org.misty.utils.flag;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class IntFlagTest {

    @Test
    public void add() {
        IntFlag intFlag = BitFlag.useInt();

        intFlag.add(1, 4);

        Assertions.assertThat(intFlag.has(1)).isTrue();
        Assertions.assertThat(intFlag.has(2)).isFalse();
        Assertions.assertThat(intFlag.has(4)).isTrue();
        Assertions.assertThat(intFlag.has(8)).isFalse();
    }

    @Test
    public void remove() {
        IntFlag intFlag = BitFlag.useInt(1 | 2 | 4 | 8);

        Assertions.assertThat(intFlag.has(1)).isTrue();
        Assertions.assertThat(intFlag.has(2)).isTrue();
        Assertions.assertThat(intFlag.has(4)).isTrue();
        Assertions.assertThat(intFlag.has(8)).isTrue();

        intFlag.remove(2, 8);

        Assertions.assertThat(intFlag.has(1)).isTrue();
        Assertions.assertThat(intFlag.has(2)).isFalse();
        Assertions.assertThat(intFlag.has(4)).isTrue();
        Assertions.assertThat(intFlag.has(8)).isFalse();
    }

    @Test
    public void reset() {
        IntFlag intFlag = BitFlag.useInt(1 | 2 | 4 | 8);

        Assertions.assertThat(intFlag.has(1)).isTrue();
        Assertions.assertThat(intFlag.has(2)).isTrue();
        Assertions.assertThat(intFlag.has(4)).isTrue();
        Assertions.assertThat(intFlag.has(8)).isTrue();

        intFlag.reset();

        Assertions.assertThat(intFlag.has(1)).isFalse();
        Assertions.assertThat(intFlag.has(2)).isFalse();
        Assertions.assertThat(intFlag.has(4)).isFalse();
        Assertions.assertThat(intFlag.has(8)).isFalse();

        intFlag.reset(2 | 8);

        Assertions.assertThat(intFlag.has(1)).isFalse();
        Assertions.assertThat(intFlag.has(2)).isTrue();
        Assertions.assertThat(intFlag.has(4)).isFalse();
        Assertions.assertThat(intFlag.has(8)).isTrue();
    }

    @Test
    public void hasAll() {
        IntFlag intFlag = BitFlag.useInt(1 | 2 | 4 | 8);

        Assertions.assertThat(intFlag.hasAll(1 | 2 | 4)).isTrue();
        Assertions.assertThat(intFlag.hasAll(1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(intFlag.hasAll(1 | 2 | 4 | 8 | 16)).isFalse();
        Assertions.assertThat(intFlag.hasAll(2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasAny() {
        IntFlag intFlag = BitFlag.useInt(2 | 4 | 8 | 16);

        Assertions.assertThat(intFlag.hasAny(1)).isFalse();
        Assertions.assertThat(intFlag.hasAny(1 | 2)).isTrue();
        Assertions.assertThat(intFlag.hasAny(2 | 4)).isTrue();
        Assertions.assertThat(intFlag.hasAny(4 | 8)).isTrue();
        Assertions.assertThat(intFlag.hasAny(8 | 16)).isTrue();
        Assertions.assertThat(intFlag.hasAny(16 | 32)).isTrue();
        Assertions.assertThat(intFlag.hasAny(32)).isFalse();
    }

    @Test
    public void hasNumber() {
        IntFlag intFlag = BitFlag.useInt(2 | 4 | 8 | 16);

        Assertions.assertThat(intFlag.hasNumber(2, 1)).isFalse();
        Assertions.assertThat(intFlag.hasNumber(2, 1 | 2)).isFalse();
        Assertions.assertThat(intFlag.hasNumber(2, 2 | 4)).isTrue();
        Assertions.assertThat(intFlag.hasNumber(2, 2 | 4 | 8)).isFalse();

        Assertions.assertThat(intFlag.hasNumber(3, 1)).isFalse();
        Assertions.assertThat(intFlag.hasNumber(3, 1 | 2)).isFalse();
        Assertions.assertThat(intFlag.hasNumber(3, 1 | 2 | 4)).isFalse();
        Assertions.assertThat(intFlag.hasNumber(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(intFlag.hasNumber(3, 1 | 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasNumberLeast() {
        IntFlag intFlag = BitFlag.useInt(2 | 4 | 8 | 16);

        Assertions.assertThat(intFlag.hasNumberLeast(2, 1)).isFalse();
        Assertions.assertThat(intFlag.hasNumberLeast(2, 1 | 2)).isFalse();
        Assertions.assertThat(intFlag.hasNumberLeast(2, 2 | 4)).isTrue();
        Assertions.assertThat(intFlag.hasNumberLeast(2, 2 | 4 | 8)).isTrue();

        Assertions.assertThat(intFlag.hasNumberLeast(3, 1)).isFalse();
        Assertions.assertThat(intFlag.hasNumberLeast(3, 1 | 2)).isFalse();
        Assertions.assertThat(intFlag.hasNumberLeast(3, 1 | 2 | 4)).isFalse();
        Assertions.assertThat(intFlag.hasNumberLeast(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(intFlag.hasNumberLeast(3, 1 | 2 | 4 | 8 | 16)).isTrue();
    }

    @Test
    public void hasNumberMost() {
        IntFlag intFlag = BitFlag.useInt(2 | 4 | 8 | 16);

        Assertions.assertThat(intFlag.hasNumberMost(2, 1)).isTrue();
        Assertions.assertThat(intFlag.hasNumberMost(2, 1 | 2)).isTrue();
        Assertions.assertThat(intFlag.hasNumberMost(2, 2 | 4)).isTrue();
        Assertions.assertThat(intFlag.hasNumberMost(2, 2 | 4 | 8)).isFalse();

        Assertions.assertThat(intFlag.hasNumberMost(3, 1)).isTrue();
        Assertions.assertThat(intFlag.hasNumberMost(3, 1 | 2)).isTrue();
        Assertions.assertThat(intFlag.hasNumberMost(3, 1 | 2 | 4)).isTrue();
        Assertions.assertThat(intFlag.hasNumberMost(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(intFlag.hasNumberMost(3, 1 | 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasNumberRange() {
        IntFlag intFlag = BitFlag.useInt(2 | 4 | 8 | 16);

        Assertions.assertThat(intFlag.hasNumberRange(2, 3, 1)).isFalse();
        Assertions.assertThat(intFlag.hasNumberRange(2, 3, 1 | 2)).isFalse();
        Assertions.assertThat(intFlag.hasNumberRange(2, 3, 2 | 4)).isTrue();
        Assertions.assertThat(intFlag.hasNumberRange(2, 3, 2 | 4 | 8)).isTrue();
        Assertions.assertThat(intFlag.hasNumberRange(2, 3, 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void tip() {
        IntFlag intFlag = BitFlag.useInt(1 | 4);
        intFlag.tip();

        Assertions.assertThat(intFlag.has(1)).isFalse();
        Assertions.assertThat(intFlag.has(2)).isTrue();
        Assertions.assertThat(intFlag.has(4)).isFalse();
        Assertions.assertThat(intFlag.has(8)).isTrue();
    }

    @Test
    public void check() {
        IntFlag intFlag = BitFlag.useInt();
        AssertionsEx.awareThrown(() -> intFlag.add(0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> intFlag.add(-1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> intFlag.add(Integer.MIN_VALUE)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> intFlag.add(3)).isInstanceOf(IllegalArgumentException.class);
    }

}
