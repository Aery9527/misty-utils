package org.misty.utils.flag;

import java.util.stream.IntStream;

/**
 * 僅支援30個bit, 也就是僅支援30個種類的項目而已, 參考{@link #MAX_BIT}說明
 */
public class IntFlag implements BitFlag {

    /**
     * 最多支援30個bit, 主要來自{@link Integer#MAX_VALUE}天生的限制,
     * 然後由於支援翻轉({@link #tip()}, 所以再去掉1個bit, 因此就只能支援30個bit.
     */
    public final static int MAX_BIT = 30;

    public static IntFlag create() {
        return new IntFlag();
    }

    public static IntFlag create(int flags) {
        return new IntFlag(flags);
    }

    private int flags = 0;

    public IntFlag() {
    }

    public IntFlag(int flags) {
        reset(flags);
    }

    public void add(int... flags) {
        IntStream.of(flags).forEach(this::add);
    }

    /**
     * @param flag 僅支援2的次方數
     */
    public void add(int flag) {
        check2Power(flag);
        this.flags |= flag;
    }

    public void remove(int... flags) {
        IntStream.of(flags).forEach(this::remove);
    }

    /**
     * @param flag 僅支援2的次方數
     */
    public void remove(int flag) {
        check2Power(flag);
        this.flags &= ~flag;
    }

    public void reset() {
        this.flags = 0;
    }

    /**
     * @param flags 支援任意數, 但也代表其內容包含多個bit
     */
    public void reset(int flags) {
        this.flags = flags;
    }

    public boolean hasAll(int flags) {
        return has(flags);
    }

    public boolean hasAny(int flags) {
        return (this.flags & flags) > 0;
    }

    /**
     * 是否有指定數量的bit
     *
     * @param amount 要符合幾個bit
     * @param flags  要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmount(int amount, int flags) {
        return hasAmountRange(amount, amount, flags);
    }

    /**
     * 是否有最少指定數量的bit
     *
     * @param least 最少要符合幾個bit
     * @param flags 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmountLeast(int least, int flags) {
        return hasAmountRange(least, Integer.MAX_VALUE, flags);
    }

    /**
     * 是否有最多指定數量的bit
     *
     * @param most  最多要符合幾個bit
     * @param flags 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmountMost(int most, int flags) {
        return hasAmountRange(0, most, flags);
    }

    /**
     * bit符合數量區間檢查
     *
     * @param least 最少要符合幾個bit
     * @param most  最多要符合幾個bit
     * @param flags 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasAmountRange(int least, int most, int flags) {
        int and = this.flags & flags;
        int count = (int) IntStream.rangeClosed(0, MAX_BIT)
                .filter(i -> (and & (1L << i)) > 0)
                .count();
        return count >= least && count <= most;
    }

    /**
     * @param flag 支援任意數, 但也代表其內容包含多個bit
     * @return 判斷當前 {@link #flags} 是否包含傳入的bit, 所有bit相同回傳true, 任一bit不對則會回傳false
     */
    public boolean has(int flag) {
        return (this.flags & flag) == flag;
    }

    /**
     * 翻轉所有bit, 也就是1變0, 0變1
     */
    public void tip() {
        this.flags = ~this.flags;
    }

    private void check2Power(int flag) {
        boolean is2Power = (flag & (flag - 1)) == 0;
        if (flag <= 0 || !is2Power) {
            String msg = Integer.toBinaryString(flag) + "b (" + flag + ")";
            throw new IllegalArgumentException(msg);
        }
    }

    public int getFlags() {
        return this.flags;
    }

}
