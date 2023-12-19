package org.misty.utils.range;

import org.misty.utils.verify.Verifier;

import java.math.BigInteger;
import java.util.Random;
import java.util.function.Supplier;

public class BigIntegerRangePreset implements BigIntegerRange {

    /**
     * <pre>
     * This approach has a probability distribution issue with random numbers
     * because the method involves segmenting based on multiples of 2
     * and obtaining random numbers within each segment.
     *
     * The distribution of random numbers within each segment is uniform,
     * but the final result involves adding up the random numbers from each segment.
     *
     * This leads to a distribution that resembles Pascal's Triangle.
     * </pre>
     */
    public class BreakDown2PowerRandom {

        Supplier<BigInteger> next;

        String msg;

        public BreakDown2PowerRandom(BigInteger gap, BigInteger lower, BigInteger upper) {
            this(gap);
            Supplier<BigInteger> next = this.next;
            this.next = () -> lower.add(next.get());
            this.msg = "Random[" + lower + "," + upper + "]:gap(" + gap + " = " + this.msg + ")";
        }

        public BreakDown2PowerRandom(BigInteger gap) {
            Verifier.requireBigIntegerMoreThanInclusive("gap", gap, BigInteger.ZERO);

            int gapBitLength = gap.bitLength();
            BigInteger pow = BigInteger.valueOf(2).pow(gapBitLength);

            int bitPow = pow.compareTo(gap) > 0 ? gapBitLength - 1 : gapBitLength;
            BigInteger gapMultipleOf2Base = BigInteger.valueOf(2).pow(bitPow);
            BigInteger gapRemainderOf2Base = gap.subtract(gapMultipleOf2Base);

            Supplier<BigInteger> random0ro1 = () -> Math.random() < 0.5 ? BigInteger.ZERO : BigInteger.ONE;

            if (bitPow == 0) {
                this.next = random0ro1;
                this.msg = "2^0";
            } else if (gapRemainderOf2Base.compareTo(BigInteger.ZERO) == 0) {
                this.next = () -> random0ro1.get().add(new BigInteger(bitPow, new Random()));
                this.msg = "2^" + bitPow;
            } else {
                BreakDown2PowerRandom gap2RemainderRandom = new BreakDown2PowerRandom(gapRemainderOf2Base);
                this.next = () -> random0ro1.get().add(new BigInteger(bitPow, new Random()).add(gap2RemainderRandom.next()));
                this.msg = "2^" + bitPow + " + " + gap2RemainderRandom;
            }
        }

        public BigInteger next() {
            return next.get();
        }

        @Override
        public String toString() {
            return this.msg;
        }
    }

    private final BigInteger lower;

    private final BigInteger upper;

    private final BigInteger gap;

    private final String rangeMsg;

    private BreakDown2PowerRandom gapRandom;

    public BigIntegerRangePreset(BigInteger lower, BigInteger upper) {
        this.lower = lower;
        this.upper = upper;
        this.gap = upper.subtract(lower);
        this.rangeMsg = getClass().getSimpleName() + Range.message(lower, upper);
    }

    @Override
    public String toString() {
        return rangeMsg;
    }

    @Override
    public BigInteger getLower() {
        return this.lower;
    }

    @Override
    public BigInteger getUpper() {
        return this.upper;
    }

    @Override
    public boolean inRange(BigInteger value) {
        return value.compareTo(this.lower) >= 0 && value.compareTo(this.upper) <= 0;
    }

    @Override
    public boolean outRange(BigInteger value) {
        return !inRange(value);
    }

    /**
     * FIXME see {@link BreakDown2PowerRandom}
     */
    @Deprecated // see BreakDown2PowerRandom
    @Override
    public BigInteger random() {
        return getGapRandom().next();
    }

    public BreakDown2PowerRandom getGapRandom() {
        if (this.gapRandom == null) {
            this.gapRandom = new BreakDown2PowerRandom(this.gap, this.lower, this.upper);
        }

        return this.gapRandom;
    }

}
