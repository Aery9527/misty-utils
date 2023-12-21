package org.misty.utils.range;

import org.misty.utils.verify.IntRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.util.function.IntConsumer;

public class IntRangeBuilder extends BaseRangeBuilder<IntRangeBuilder> {

    private IntConsumer lowerBoundVerify = value -> {
    };

    private IntConsumer upperBoundVerify = value -> {
    };

    public IntRangeBuilder(String title) {
        super(title);
    }

    public IntRangeBuilder giveLowerBound(int min, int max) {
        Verifier.requireIntLessThanInclusive(getTitle(), "lowerMin", min, "lowerMax", max);
        IntRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public IntRangeBuilder giveUpperBound(int min, int max) {
        Verifier.requireIntLessThanInclusive(getTitle(), "upperMin", min, "upperMax", max);
        IntRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public IntRange build(int lower, int upper) {
        this.lowerBoundVerify.accept(lower);
        this.upperBoundVerify.accept(upper);
        Verifier.requireIntLessThanInclusive(getTitle(), "lower", lower, "upper", upper);

        return new IntRangePreset(lower, upper);
    }

}
