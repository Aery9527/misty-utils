package org.misty.utils.range;

import org.misty.utils.verify.DoubleRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.util.function.DoubleConsumer;

public class DoubleRangeBuilder {

    private DoubleConsumer lowerBoundVerify = value -> {
    };

    private DoubleConsumer upperBoundVerify = value -> {
    };

    public DoubleRangeBuilder giveLowerBound(double min, double max) {
        verifyInvalidValue("lowerMin", min);
        verifyInvalidValue("lowerMax", max);
        Verifier.requireDoubleLessThanInclusive("lowerMin", min, "lowerMax", max);

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public DoubleRangeBuilder giveUpperBound(double min, double max) {
        verifyInvalidValue("upperMin", min);
        verifyInvalidValue("upperMax", max);
        Verifier.requireDoubleLessThanInclusive("upperMin", min, "upperMax", max);

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public DoubleRange build(double lower, double upper) {
        verifyInvalidValue("lower", lower);
        verifyInvalidValue("upper", upper);

        this.lowerBoundVerify.accept(lower);
        this.upperBoundVerify.accept(upper);
        Verifier.requireDoubleLessThanInclusive("lower", lower, "upper", upper);

        return new DoubleRangePreset(lower, upper);
    }

    private void verifyInvalidValue(String term, double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(term + "(" + value + ") Invalid");
        }
    }

}
