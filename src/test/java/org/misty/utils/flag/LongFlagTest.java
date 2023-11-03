package org.misty.utils.flag;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class LongFlagTest {

    @Test
    public void add() {
        LongFlag longFlag = BitFlag.useLong();

        longFlag.add(1, 4);

        Assertions.assertThat(longFlag.has(1)).isTrue();
        Assertions.assertThat(longFlag.has(2)).isFalse();
        Assertions.assertThat(longFlag.has(4)).isTrue();
        Assertions.assertThat(longFlag.has(8)).isFalse();
    }

    @Test
    public void remove() {
        LongFlag longFlag = BitFlag.useLong(1 | 2 | 4 | 8);

        Assertions.assertThat(longFlag.has(1)).isTrue();
        Assertions.assertThat(longFlag.has(2)).isTrue();
        Assertions.assertThat(longFlag.has(4)).isTrue();
        Assertions.assertThat(longFlag.has(8)).isTrue();

        longFlag.remove(2, 8);

        Assertions.assertThat(longFlag.has(1)).isTrue();
        Assertions.assertThat(longFlag.has(2)).isFalse();
        Assertions.assertThat(longFlag.has(4)).isTrue();
        Assertions.assertThat(longFlag.has(8)).isFalse();
    }

    @Test
    public void reset() {
        LongFlag longFlag = BitFlag.useLong(1 | 2 | 4 | 8);

        Assertions.assertThat(longFlag.has(1)).isTrue();
        Assertions.assertThat(longFlag.has(2)).isTrue();
        Assertions.assertThat(longFlag.has(4)).isTrue();
        Assertions.assertThat(longFlag.has(8)).isTrue();

        longFlag.reset();

        Assertions.assertThat(longFlag.has(1)).isFalse();
        Assertions.assertThat(longFlag.has(2)).isFalse();
        Assertions.assertThat(longFlag.has(4)).isFalse();
        Assertions.assertThat(longFlag.has(8)).isFalse();

        longFlag.reset(2 | 8);

        Assertions.assertThat(longFlag.has(1)).isFalse();
        Assertions.assertThat(longFlag.has(2)).isTrue();
        Assertions.assertThat(longFlag.has(4)).isFalse();
        Assertions.assertThat(longFlag.has(8)).isTrue();
    }

    @Test
    public void hasAll() {
        LongFlag longFlag = BitFlag.useLong(1 | 2 | 4 | 8);

        Assertions.assertThat(longFlag.hasAll(1 | 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasAll(1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasAll(1 | 2 | 4 | 8 | 16)).isFalse();
        Assertions.assertThat(longFlag.hasAll(2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasAny() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasAny(1)).isFalse();
        Assertions.assertThat(longFlag.hasAny(1 | 2)).isTrue();
        Assertions.assertThat(longFlag.hasAny(2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasAny(4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasAny(8 | 16)).isTrue();
        Assertions.assertThat(longFlag.hasAny(16 | 32)).isTrue();
        Assertions.assertThat(longFlag.hasAny(32)).isFalse();
    }

    @Test
    public void hasAmount() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasAmount(2, 1)).isFalse();
        Assertions.assertThat(longFlag.hasAmount(2, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasAmount(2, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasAmount(2, 2 | 4 | 8)).isFalse();

        Assertions.assertThat(longFlag.hasAmount(3, 1)).isFalse();
        Assertions.assertThat(longFlag.hasAmount(3, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasAmount(3, 1 | 2 | 4)).isFalse();
        Assertions.assertThat(longFlag.hasAmount(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasAmount(3, 1 | 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasAmountLeast() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasAmountLeast(2, 1)).isFalse();
        Assertions.assertThat(longFlag.hasAmountLeast(2, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasAmountLeast(2, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasAmountLeast(2, 2 | 4 | 8)).isTrue();

        Assertions.assertThat(longFlag.hasAmountLeast(3, 1)).isFalse();
        Assertions.assertThat(longFlag.hasAmountLeast(3, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasAmountLeast(3, 1 | 2 | 4)).isFalse();
        Assertions.assertThat(longFlag.hasAmountLeast(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasAmountLeast(3, 1 | 2 | 4 | 8 | 16)).isTrue();
    }

    @Test
    public void hasAmountMost() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasAmountMost(2, 1)).isTrue();
        Assertions.assertThat(longFlag.hasAmountMost(2, 1 | 2)).isTrue();
        Assertions.assertThat(longFlag.hasAmountMost(2, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasAmountMost(2, 2 | 4 | 8)).isFalse();

        Assertions.assertThat(longFlag.hasAmountMost(3, 1)).isTrue();
        Assertions.assertThat(longFlag.hasAmountMost(3, 1 | 2)).isTrue();
        Assertions.assertThat(longFlag.hasAmountMost(3, 1 | 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasAmountMost(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasAmountMost(3, 1 | 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasAmountRange() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasAmountRange(2, 3, 1)).isFalse();
        Assertions.assertThat(longFlag.hasAmountRange(2, 3, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasAmountRange(2, 3, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasAmountRange(2, 3, 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasAmountRange(2, 3, 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void tip() {
        LongFlag longFlag = BitFlag.useLong(1 | 4);
        longFlag.tip();

        Assertions.assertThat(longFlag.has(1)).isFalse();
        Assertions.assertThat(longFlag.has(2)).isTrue();
        Assertions.assertThat(longFlag.has(4)).isFalse();
        Assertions.assertThat(longFlag.has(8)).isTrue();
    }

    @Test
    public void check() {
        LongFlag longFlag = BitFlag.useLong();
        AssertionsEx.assertThrown(() -> longFlag.add(0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> longFlag.add(-1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> longFlag.add(Long.MIN_VALUE)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> longFlag.add(3)).isInstanceOf(IllegalArgumentException.class);
    }

}
