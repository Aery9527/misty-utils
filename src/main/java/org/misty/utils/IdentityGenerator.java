package org.misty.utils;

import org.misty.utils.range.IntRange;
import org.misty.utils.range.Range;

public class IdentityGenerator {

    private static final IntRange RADIX_VERIFIER = Range.intRangeBuilder().build(Character.MIN_RADIX, Character.MAX_RADIX);

    private static final int INACTIVE_THRESHOLD = 0;

    /**
     * {@link #genWithTimestamp(int)}
     */
    public static String genWithTimestamp() {
        long receptor = System.currentTimeMillis();
        return gen(receptor);
    }

    /**
     * {@link #genWithTimestamp(int, int)}
     */
    public static String genWithTimestamp(int suffixAppendNumber) {
        return genWithTimestamp(INACTIVE_THRESHOLD, suffixAppendNumber);
    }

    /**
     * 根據當前的timestamp以36位元換算({@link Character#MAX_RADIX})
     *
     * @param suffixCutNumber    {@link #gen(long, int, int, int)}
     * @param suffixAppendNumber {@link #gen(long, int, int, int)}
     * @return
     */
    public static String genWithTimestamp(int suffixCutNumber, int suffixAppendNumber) {
        long receptor = System.currentTimeMillis();
        int radix = Character.MAX_RADIX;
        return gen(receptor, radix, suffixCutNumber, suffixAppendNumber);
    }

    /**
     * {@link #gen(long, int, int, int)}
     *
     * @param receptor
     * @return
     */
    public static String gen(long receptor) {
        int radix = Character.MAX_RADIX;
        return gen(receptor, radix);
    }

    /**
     * {@link #gen(long, int, int, int)}
     *
     * @param receptor
     * @param radix
     * @return
     */
    public static String gen(long receptor, int radix) {
        return gen(receptor, radix, INACTIVE_THRESHOLD);
    }

    /**
     * {@link #gen(long, int, int, int)}
     *
     * @param receptor
     * @param radix
     * @param suffixCutNumber
     * @return
     */
    public static String gen(long receptor, int radix, int suffixCutNumber) {
        return gen(receptor, radix, suffixCutNumber, INACTIVE_THRESHOLD);
    }

    /**
     * @param receptor           要進行進位換算的受體數字
     * @param radix              要對受體進行多少進位的換算, 範圍{@link Character#MIN_RADIX}~{@link Character#MAX_RADIX}
     * @param suffixCutNumber    換算後要取得最後面幾個字串, 小於0就不做取最後幾個字元的動作, 若受體換算出來長度小於該長度也不動作
     * @param suffixAppendNumber 要在後綴加上幾個隨機數字, 這個加入的動作在suffixCutNumber處理之後, 因此結果長度將是suffixCutNumber加上此數字
     * @return 換算出來的結果
     */
    public static String gen(long receptor, int radix, int suffixCutNumber, int suffixAppendNumber) {
        RADIX_VERIFIER.inRange(radix);

        String result = Long.toString(receptor, radix);
        int resultRawLength = result.length();

        boolean needFetchSuffix = suffixCutNumber > INACTIVE_THRESHOLD && suffixCutNumber < resultRawLength;
        if (needFetchSuffix) {
            result = result.substring(resultRawLength - suffixCutNumber);
        }

        boolean needAppendSuffixRandomNumber = suffixAppendNumber > INACTIVE_THRESHOLD;
        if (needAppendSuffixRandomNumber) {
            Double random = Math.random() * Math.pow(10, suffixAppendNumber);
            result += random.longValue(); // random會無條件捨去小數來換算出long
        }

        return result;
    }

}
