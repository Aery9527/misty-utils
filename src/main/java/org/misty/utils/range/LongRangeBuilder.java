package org.misty.utils.range;

import org.misty.utils.verify.LongRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.util.function.LongConsumer;

public class LongRangeBuilder {

    private LongConsumer lowerBoundVerify = value -> {
    };

    private LongConsumer upperBoundVerify = value -> {
    };

    public LongRangeBuilder giveLowerBound(long min, long max) {
        Verifier.requireLongLessThanInclusive("lowerMin", min, "lowerMax", max);
        LongRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public LongRangeBuilder giveUpperBound(long min, long max) {
        Verifier.requireLongLessThanInclusive("upperMin", min, "upperMax", max);
        LongRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public LongRange build(long lower, long upper) {
        this.lowerBoundVerify.accept(lower);
        this.upperBoundVerify.accept(upper);
        Verifier.requireLongLessThanInclusive("lower", lower, "upper", upper);

        return new LongRangePreset(lower, upper);
    }

}
