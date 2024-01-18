package org.misty.utils.flag;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * 僅支援62個bit, 也就是僅支援62個種類的項目而已, 參考{@link #MAX_BIT}說明
 */
public class LongFlag implements BitFlag {

    /**
     * 最多支援62個bit, 主要來自{@link Long#MAX_VALUE}天生的限制,
     * 然後由於支援翻轉({@link #tip()}, 所以再去掉1個bit, 因此就只能支援62個bit.
     */
    public final static int MAX_BIT = 62;

    public static LongFlag create() {
        return new LongFlag();
    }

    public static LongFlag create(long flags) {
        return new LongFlag(flags);
    }

    private long flags = 0;

    public LongFlag() {
    }

    public LongFlag(long flags) {
        reset(flags);
    }

    public void add(long... flags) {
        LongStream.of(flags).forEach(this::add);
    }

    /**
     * @param flag 僅支援2的次方數
     */
    public void add(long flag) {
        check2Power(flag);
        this.flags |= flag;
    }

    public void remove(long... flags) {
        LongStream.of(flags).forEach(this::remove);
    }

    /**
     * @param flag 僅支援2的次方數
     */
    public void remove(long flag) {
        check2Power(flag);
        this.flags &= ~flag;
    }

    public void reset() {
        this.flags = 0;
    }

    /**
     * @param flags 支援任意數, 但也代表其內容包含多個bit
     */
    public void reset(long flags) {
        this.flags = flags;
    }

    public boolean hasAll(long flags) {
        return has(flags);
    }

    public boolean hasAny(long flags) {
        return (this.flags & flags) > 0;
    }

    /**
     * 是否有指定數量的bit
     *
     * @param number 要符合幾個bit
     * @param flags  要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasNumber(int number, long flags) {
        return hasNumberRange(number, number, flags);
    }

    /**
     * 是否有最少指定數量的bit
     *
     * @param least 最少要符合幾個bit
     * @param flags 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasNumberLeast(int least, long flags) {
        return hasNumberRange(least, Integer.MAX_VALUE, flags);
    }

    /**
     * 是否有最多指定數量的bit
     *
     * @param most  最多要符合幾個bit
     * @param flags 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasNumberMost(int most, long flags) {
        return hasNumberRange(0, most, flags);
    }

    /**
     * bit符合數量區間檢查
     *
     * @param least 最少要符合幾個bit
     * @param most  最多要符合幾個bit
     * @param flags 要檢查的bit
     * @return 是否符合區間
     */
    public boolean hasNumberRange(int least, int most, long flags) {
        long and = this.flags & flags;
        long count = IntStream.rangeClosed(0, MAX_BIT)
                .filter(i -> (and & (1L << i)) > 0)
                .count();
        return count >= least && count <= most;
    }

    /**
     * @param flag 支援任意數, 但也代表其內容包含多個bit
     * @return 判斷當前 {@link #flags} 是否包含傳入的bit, 所有bit相同回傳true, 任一bit不對則會回傳false
     */
    public boolean has(long flag) {
        return (this.flags & flag) == flag;
    }

    /**
     * 翻轉所有bit, 也就是1變0, 0變1
     */
    public void tip() {
        this.flags = ~this.flags;
    }

    private void check2Power(long flag) {
        boolean is2Power = (flag & (flag - 1)) == 0;
        if (flag <= 0 || !is2Power) {
            String msg = Long.toBinaryString(flag) + "b (" + flag + ")";
            throw new IllegalArgumentException(msg);
        }
    }

    public long getFlags() {
        return this.flags;
    }

}
