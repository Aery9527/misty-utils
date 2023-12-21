package org.misty.utils.range;

import org.misty.utils.verify.BigIntegerRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.math.BigInteger;
import java.util.function.Consumer;

public class BigIntegerRangeBuilder extends BaseRangeBuilder<BigIntegerRangeBuilder> {

    private Consumer<BigInteger> lowerBoundVerify = value -> {
    };

    private Consumer<BigInteger> upperBoundVerify = value -> {
    };

    public BigIntegerRangeBuilder(String title) {
        super(title);
    }

    public BigIntegerRangeBuilder giveLowerBound(long min, long max) {
        return giveLowerBound(BigInteger.valueOf(min), BigInteger.valueOf(max));
    }

    public BigIntegerRangeBuilder giveLowerBound(BigInteger min, BigInteger max) {
        Verifier.requireBigIntegerLessThanInclusive(getTitle(), "lowerMin", min, "lowerMax", max);
        BigIntegerRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public BigIntegerRangeBuilder giveUpperBound(long min, long max) {
        return giveUpperBound(BigInteger.valueOf(min), BigInteger.valueOf(max));
    }

    public BigIntegerRangeBuilder giveUpperBound(BigInteger min, BigInteger max) {
        Verifier.requireBigIntegerLessThanInclusive(getTitle(), "upperMin", min, "upperMax", max);
        BigIntegerRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public BigIntegerRange build(BigInteger lower, BigInteger upper) {
        this.lowerBoundVerify.accept(lower);
        this.upperBoundVerify.accept(upper);
        Verifier.requireBigIntegerLessThanInclusive(getTitle(), "lower", lower, "upper", upper);

        return new BigIntegerRangePreset(lower, upper);
    }

}
