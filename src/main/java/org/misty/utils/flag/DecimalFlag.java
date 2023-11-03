package org.misty.utils.flag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 支援到2^32個bit, 且改為一般十進制表達, 不像{@link IntFlag}與{@link LongFlag}那樣限制2的倍數操作, 不支援反轉操作
 */
public class DecimalFlag implements BitFlag {

    /**
     * 最多支援2^32個bit, 因為負號也算不同的bit
     */
    public final static long MAX_BIT = 1L << 32;

    public final static int GROUP_NUMBER = LongFlag.MAX_BIT;

    public static DecimalFlag create() {
        return new DecimalFlag();
    }

    public static DecimalFlag create(int number) {
        return new DecimalFlag(number);
    }

    public static DecimalFlag create(int... numbers) {
        return new DecimalFlag(numbers);
    }

    public static DecimalFlag create(Collection<Integer> numbers) {
        return new DecimalFlag(numbers);
    }

    private final Map<Integer, LongFlag> flagGroup = new HashMap<>();

    public DecimalFlag() {
    }

    public DecimalFlag(int number) {
        add(number);
    }

    public DecimalFlag(int[] numbers) {
        add(numbers);
    }

    public DecimalFlag(Collection<Integer> numbers) {
        numbers.stream().mapToInt(Integer::intValue).forEach(this::add);
    }

    public void add(int... numbers) {
        IntStream.of(numbers).forEach(this::add);
    }

    public void add(int number) {
        int groupLevel = getGroupLevel(number);
        long flag = getGroupFlag(number, groupLevel);
        this.flagGroup.computeIfAbsent(groupLevel, k -> LongFlag.create()).add(flag);
    }

    public void remove(int... numbers) {
        IntStream.of(numbers).forEach(this::remove);
    }

    public void remove(int number) {
        int groupLevel = getGroupLevel(number);

        LongFlag longFlag = this.flagGroup.get(groupLevel);
        if (longFlag == null) {
            return;
        }

        long flag = getGroupFlag(number, groupLevel);
        longFlag.remove(flag);
    }

    public void reset() {
        this.flagGroup.clear();
    }

    public void reset(int... numbers) {
        reset();
        add(numbers);
    }

    public boolean hasAll(int... numbers) {
        return IntStream.of(numbers).allMatch(this::has);
    }

    public boolean hasAny(int... numbers) {
        return IntStream.of(numbers).anyMatch(this::has);
    }

    /**
     * 是否有指定數量的bit
     *
     * @param amount  要符合幾個bit
     * @param numbers 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmount(int amount, int... numbers) {
        return hasAmountRange(amount, amount, numbers);
    }

    /**
     * 是否有最少指定數量的bit
     *
     * @param least   最少要符合幾個bit
     * @param numbers 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmountLeast(int least, int... numbers) {
        return hasAmountRange(least, Integer.MAX_VALUE, numbers);
    }

    /**
     * 是否有最多指定數量的bit
     *
     * @param most    最多要符合幾個bit
     * @param numbers 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmountMost(int most, int... numbers) {
        return hasAmountRange(0, most, numbers);
    }

    /**
     * bit符合數量區間檢查
     *
     * @param least  最少要符合幾個bit
     * @param most   最多要符合幾個bit
     * @param number 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmountRange(int least, int most, int... number) {
        int count = (int) IntStream.of(number).filter(this::has).count();
        return count >= least && count <= most;
    }

    public boolean has(int number) {
        int groupLevel = getGroupLevel(number);

        LongFlag longFlag = this.flagGroup.get(groupLevel);
        if (longFlag == null) {
            return false;
        }

        long flag = getGroupFlag(number, groupLevel);
        return longFlag.has(flag);
    }

    protected int getGroupLevel(int number) {
        int quotient = number / GROUP_NUMBER;
        return number >= 0 ? quotient : quotient - 1;
    }

    protected long getGroupFlag(int number, int groupLevel) {
        groupLevel = groupLevel >= 0 ? groupLevel : groupLevel + 1;
        int groupContent = Math.abs(number - (groupLevel * GROUP_NUMBER));
        return 1L << groupContent;
    }

}
