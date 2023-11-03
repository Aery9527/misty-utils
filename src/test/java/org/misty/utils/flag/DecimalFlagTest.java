package org.misty.utils.flag;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DecimalFlagTest {

    @Test
    public void getGroupLevel() {
        DecimalFlag decimalFlag = BitFlag.useDecimal();

        int number = 0;
        Assertions.assertThat(decimalFlag.getGroupLevel(number)).isEqualTo(0);
        Assertions.assertThat(decimalFlag.getGroupLevel(-number)).isEqualTo(0);

        number = 1;
        Assertions.assertThat(decimalFlag.getGroupLevel(number)).isEqualTo(0);
        Assertions.assertThat(decimalFlag.getGroupLevel(-number)).isEqualTo(-1);

        number = DecimalFlag.GROUP_NUMBER - 1;
        Assertions.assertThat(decimalFlag.getGroupLevel(number)).isEqualTo(0);
        Assertions.assertThat(decimalFlag.getGroupLevel(-number)).isEqualTo(-1);

        number = DecimalFlag.GROUP_NUMBER;
        Assertions.assertThat(decimalFlag.getGroupLevel(number)).isEqualTo(1);
        Assertions.assertThat(decimalFlag.getGroupLevel(-number)).isEqualTo(-2);

        number = DecimalFlag.GROUP_NUMBER + 1;
        Assertions.assertThat(decimalFlag.getGroupLevel(number)).isEqualTo(1);
        Assertions.assertThat(decimalFlag.getGroupLevel(-number)).isEqualTo(-2);

        number = Integer.MAX_VALUE;
        Assertions.assertThat(decimalFlag.getGroupLevel(number)).isEqualTo(34636833);
        Assertions.assertThat(decimalFlag.getGroupLevel(-number)).isEqualTo(-34636834);
    }

    @Test
    public void getGroupFlag() {
        DecimalFlag decimalFlag = BitFlag.useDecimal();

        int number = 0;
        int groupLevel = decimalFlag.getGroupLevel(number);
        long groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(1);
        number = -0;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(1);

        number = 1;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(2);
        number = -1;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(2);

        number = 2;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(4);
        number = -2;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(4);

        number = 3;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(8);
        number = -3;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(8);


        number = DecimalFlag.GROUP_NUMBER - 1;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(1L << (DecimalFlag.GROUP_NUMBER - 1));
        number = -DecimalFlag.GROUP_NUMBER + 1;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(1L << (DecimalFlag.GROUP_NUMBER - 1));

        number = DecimalFlag.GROUP_NUMBER;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(1);
        number = -DecimalFlag.GROUP_NUMBER;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(1);

        number = DecimalFlag.GROUP_NUMBER + 1;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(2);
        number = -DecimalFlag.GROUP_NUMBER - 1;
        groupLevel = decimalFlag.getGroupLevel(number);
        groupFlag = decimalFlag.getGroupFlag(number, groupLevel);
        Assertions.assertThat(groupFlag).isEqualTo(2);
    }

    @Test
    public void add() {
        DecimalFlag decimalFlag = BitFlag.useDecimal();
        decimalFlag.add(1, 3);

        Assertions.assertThat(decimalFlag.has(1)).isTrue();
        Assertions.assertThat(decimalFlag.has(2)).isFalse();
        Assertions.assertThat(decimalFlag.has(3)).isTrue();
        Assertions.assertThat(decimalFlag.has(4)).isFalse();

        Assertions.assertThat(decimalFlag.has(DecimalFlag.GROUP_NUMBER - 1)).isFalse();
        Assertions.assertThat(decimalFlag.has(DecimalFlag.GROUP_NUMBER)).isFalse();
        Assertions.assertThat(decimalFlag.has(DecimalFlag.GROUP_NUMBER + 1)).isFalse();
        decimalFlag.add(DecimalFlag.GROUP_NUMBER - 1);
        decimalFlag.add(DecimalFlag.GROUP_NUMBER);
        decimalFlag.add(DecimalFlag.GROUP_NUMBER + 1);
        Assertions.assertThat(decimalFlag.has(DecimalFlag.GROUP_NUMBER - 1)).isTrue();
        Assertions.assertThat(decimalFlag.has(DecimalFlag.GROUP_NUMBER)).isTrue();
        Assertions.assertThat(decimalFlag.has(DecimalFlag.GROUP_NUMBER + 1)).isTrue();

        Assertions.assertThat(decimalFlag.has(Integer.MAX_VALUE)).isFalse();
        Assertions.assertThat(decimalFlag.has(Integer.MIN_VALUE)).isFalse();
        decimalFlag.add(Integer.MAX_VALUE);
        decimalFlag.add(Integer.MIN_VALUE);
        Assertions.assertThat(decimalFlag.has(Integer.MAX_VALUE)).isTrue();
        Assertions.assertThat(decimalFlag.has(Integer.MIN_VALUE)).isTrue();
    }

    @Test
    public void remove() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.has(1)).isTrue();
        Assertions.assertThat(decimalFlag.has(2)).isTrue();
        Assertions.assertThat(decimalFlag.has(3)).isTrue();
        Assertions.assertThat(decimalFlag.has(4)).isTrue();

        decimalFlag.remove(2, 4);

        Assertions.assertThat(decimalFlag.has(1)).isTrue();
        Assertions.assertThat(decimalFlag.has(2)).isFalse();
        Assertions.assertThat(decimalFlag.has(3)).isTrue();
        Assertions.assertThat(decimalFlag.has(4)).isFalse();
    }

    @Test
    public void reset() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.has(1)).isTrue();
        Assertions.assertThat(decimalFlag.has(2)).isTrue();
        Assertions.assertThat(decimalFlag.has(3)).isTrue();
        Assertions.assertThat(decimalFlag.has(4)).isTrue();

        decimalFlag.reset();

        Assertions.assertThat(decimalFlag.has(1)).isFalse();
        Assertions.assertThat(decimalFlag.has(2)).isFalse();
        Assertions.assertThat(decimalFlag.has(3)).isFalse();
        Assertions.assertThat(decimalFlag.has(4)).isFalse();

        decimalFlag.reset(1, 3);

        Assertions.assertThat(decimalFlag.has(1)).isTrue();
        Assertions.assertThat(decimalFlag.has(2)).isFalse();
        Assertions.assertThat(decimalFlag.has(3)).isTrue();
        Assertions.assertThat(decimalFlag.has(4)).isFalse();
    }

    @Test
    public void hasAll() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.hasAll(1, 2, 3)).isTrue();
        Assertions.assertThat(decimalFlag.hasAll(1, 2, 3, 4)).isTrue();
        Assertions.assertThat(decimalFlag.hasAll(1, 2, 3, 4, 5)).isFalse();
    }

    @Test
    public void hasAny() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.hasAny(0)).isFalse();
        Assertions.assertThat(decimalFlag.hasAny(0, 1)).isTrue();
        Assertions.assertThat(decimalFlag.hasAny(1, 2)).isTrue();
        Assertions.assertThat(decimalFlag.hasAny(2, 3)).isTrue();
        Assertions.assertThat(decimalFlag.hasAny(3, 4)).isTrue();
        Assertions.assertThat(decimalFlag.hasAny(4, 5)).isTrue();
        Assertions.assertThat(decimalFlag.hasAny(5)).isFalse();
    }

    @Test
    public void hasAmount() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.hasAmount(2, 0)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmount(2, 0, 1)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmount(2, 1, 2)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmount(2, 1, 2, 3)).isFalse();

        Assertions.assertThat(decimalFlag.hasAmount(3, 0, 1)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmount(3, 1, 2)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmount(3, 1, 2, 3)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmount(3, 1, 2, 3, 4)).isFalse();
    }

    @Test
    public void hasAmountLeast() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.hasAmountLeast(2, 0)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmountLeast(2, 0, 1)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmountLeast(2, 1, 2)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountLeast(2, 1, 2, 3)).isTrue();

        Assertions.assertThat(decimalFlag.hasAmountLeast(3, 0, 1)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmountLeast(3, 1, 2)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmountLeast(3, 1, 2, 3)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountLeast(3, 1, 2, 3, 4)).isTrue();
    }

    @Test
    public void hasAmountMost() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.hasAmountMost(2, 0)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountMost(2, 0, 1)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountMost(2, 1, 2)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountMost(2, 1, 2, 3)).isFalse();

        Assertions.assertThat(decimalFlag.hasAmountMost(3, 0, 1)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountMost(3, 1, 2)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountMost(3, 1, 2, 3)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountMost(3, 1, 2, 3, 4)).isFalse();
    }

    @Test
    public void hasAmountRange() {
        DecimalFlag decimalFlag = BitFlag.useDecimal(1, 2, 3, 4);

        Assertions.assertThat(decimalFlag.hasAmountRange(2, 3, 0)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmountRange(2, 3, 0, 1)).isFalse();
        Assertions.assertThat(decimalFlag.hasAmountRange(2, 3, 1, 2)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountRange(2, 3, 1, 2, 3)).isTrue();
        Assertions.assertThat(decimalFlag.hasAmountRange(2, 3, 1, 2, 3, 4)).isFalse();
    }

}
