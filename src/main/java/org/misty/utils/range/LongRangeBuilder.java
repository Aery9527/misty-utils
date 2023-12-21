package org.misty.utils.range;

import org.misty.utils.verify.LongRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.util.function.LongConsumer;

public class LongRangeBuilder extends BaseRangeBuilder<LongRangeBuilder> {

    private LongConsumer lowerBoundVerify = value -> {
    };

    private LongConsumer upperBoundVerify = value -> {
    };

    public LongRangeBuilder(String title) {
        super(title);
    }

    public LongRangeBuilder giveLowerBound(long min, long max) {
        Verifier.requireLongLessThanInclusive(getTitle(), "lowerMin", min, "lowerMax", max);
        LongRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public LongRangeBuilder giveUpperBound(long min, long max) {
        Verifier.requireLongLessThanInclusive(getTitle(), "upperMin", min, "upperMax", max);
        LongRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public LongRange build(long lower, long upper) {
        this.lowerBoundVerify.accept(lower);
        this.upperBoundVerify.accept(upper);
        Verifier.requireLongLessThanInclusive(getTitle(), "lower", lower, "upper", upper);

        return new LongRangePreset(lower, upper);
    }

}
