package org.misty.utils.flag;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class LongFlagTest {

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
    public void hasNumber() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasNumber(2, 1)).isFalse();
        Assertions.assertThat(longFlag.hasNumber(2, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasNumber(2, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasNumber(2, 2 | 4 | 8)).isFalse();

        Assertions.assertThat(longFlag.hasNumber(3, 1)).isFalse();
        Assertions.assertThat(longFlag.hasNumber(3, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasNumber(3, 1 | 2 | 4)).isFalse();
        Assertions.assertThat(longFlag.hasNumber(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasNumber(3, 1 | 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasNumberLeast() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasNumberLeast(2, 1)).isFalse();
        Assertions.assertThat(longFlag.hasNumberLeast(2, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasNumberLeast(2, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasNumberLeast(2, 2 | 4 | 8)).isTrue();

        Assertions.assertThat(longFlag.hasNumberLeast(3, 1)).isFalse();
        Assertions.assertThat(longFlag.hasNumberLeast(3, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasNumberLeast(3, 1 | 2 | 4)).isFalse();
        Assertions.assertThat(longFlag.hasNumberLeast(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasNumberLeast(3, 1 | 2 | 4 | 8 | 16)).isTrue();
    }

    @Test
    public void hasNumberMost() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasNumberMost(2, 1)).isTrue();
        Assertions.assertThat(longFlag.hasNumberMost(2, 1 | 2)).isTrue();
        Assertions.assertThat(longFlag.hasNumberMost(2, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasNumberMost(2, 2 | 4 | 8)).isFalse();

        Assertions.assertThat(longFlag.hasNumberMost(3, 1)).isTrue();
        Assertions.assertThat(longFlag.hasNumberMost(3, 1 | 2)).isTrue();
        Assertions.assertThat(longFlag.hasNumberMost(3, 1 | 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasNumberMost(3, 1 | 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasNumberMost(3, 1 | 2 | 4 | 8 | 16)).isFalse();
    }

    @Test
    public void hasNumberRange() {
        LongFlag longFlag = BitFlag.useLong(2 | 4 | 8 | 16);

        Assertions.assertThat(longFlag.hasNumberRange(2, 3, 1)).isFalse();
        Assertions.assertThat(longFlag.hasNumberRange(2, 3, 1 | 2)).isFalse();
        Assertions.assertThat(longFlag.hasNumberRange(2, 3, 2 | 4)).isTrue();
        Assertions.assertThat(longFlag.hasNumberRange(2, 3, 2 | 4 | 8)).isTrue();
        Assertions.assertThat(longFlag.hasNumberRange(2, 3, 2 | 4 | 8 | 16)).isFalse();
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
        AssertionsEx.awareThrown(() -> longFlag.add(0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> longFlag.add(-1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> longFlag.add(Long.MIN_VALUE)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> longFlag.add(3)).isInstanceOf(IllegalArgumentException.class);
    }

}
