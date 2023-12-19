package org.misty.utils.range;

import org.misty.utils.fi.FloatConsumerEx;
import org.misty.utils.verify.FloatRangeVerifier;
import org.misty.utils.verify.Verifier;

public class FloatRangeBuilder {

    private FloatConsumerEx lowerBoundVerify = value -> {
    };

    private FloatConsumerEx upperBoundVerify = value -> {
    };

    public FloatRangeBuilder giveLowerBound(float min, float max) {
        verifyInvalidValue("lowerMin", min);
        verifyInvalidValue("lowerMax", max);
        Verifier.requireFloatLessThanInclusive("lowerMin", min, "lowerMax", max);

        FloatRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public FloatRangeBuilder giveUpperBound(float min, float max) {
        verifyInvalidValue("upperMin", min);
        verifyInvalidValue("upperMax", max);
        Verifier.requireFloatLessThanInclusive("upperMin", min, "upperMax", max);

        FloatRangeVerifier rangeVerifier = Verifier.ofRange(min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public FloatRange build(float lower, float upper) {
        verifyInvalidValue("lower", lower);
        verifyInvalidValue("upper", upper);

        this.lowerBoundVerify.execute(lower);
        this.upperBoundVerify.execute(upper);
        Verifier.requireFloatLessThanInclusive("lower", lower, "upper", upper);

        return new FloatRangePreset(lower, upper);
    }

    private void verifyInvalidValue(String term, float value) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            throw new IllegalArgumentException(term + "(" + value + ") Invalid");
        }
    }

}
