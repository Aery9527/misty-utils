package org.misty.utils.range;

import org.misty.utils.fi.ShortConsumerEx;
import org.misty.utils.verify.ShortRangeVerifier;
import org.misty.utils.verify.Verifier;

public class ShortRangeBuilder {

    private ShortConsumerEx lowerBoundVerify = value -> {
    };

    private ShortConsumerEx upperBoundVerify = value -> {
    };

    public ShortRangeBuilder giveLowerBound(short min, short max) {
        Verifier.requireShortLessThanInclusive("lowerMin", min, "lowerMax", max);
        ShortRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public ShortRangeBuilder giveUpperBound(short min, short max) {
        Verifier.requireShortLessThanInclusive("upperMin", min, "upperMax", max);
        ShortRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public ShortRange build(short lower, short upper) {
        this.lowerBoundVerify.execute(lower);
        this.upperBoundVerify.execute(upper);
        Verifier.requireShortLessThanInclusive("lower", lower, "upper", upper);

        return new ShortRangePreset(lower, upper);
    }

}
