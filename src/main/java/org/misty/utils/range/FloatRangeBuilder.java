package org.misty.utils.range;

import org.misty.utils.fi.FloatConsumerEx;
import org.misty.utils.verify.FloatRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierLogic;

public class FloatRangeBuilder extends BaseRangeBuilder<FloatRangeBuilder> {

    private FloatConsumerEx lowerBoundVerify = value -> {
    };

    private FloatConsumerEx upperBoundVerify = value -> {
    };

    public FloatRangeBuilder(String title) {
        super(title);
    }

    public FloatRangeBuilder giveLowerBound(float min, float max) {
        verifyInvalidValue("lowerMin", min);
        verifyInvalidValue("lowerMax", max);
        Verifier.requireFloatLessThanInclusive(getTitle(), "lowerMin", min, "lowerMax", max);

        FloatRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public FloatRangeBuilder giveUpperBound(float min, float max) {
        verifyInvalidValue("upperMin", min);
        verifyInvalidValue("upperMax", max);
        Verifier.requireFloatLessThanInclusive(getTitle(), "upperMin", min, "upperMax", max);

        FloatRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public FloatRange build(float lower, float upper) {
        verifyInvalidValue("lower", lower);
        verifyInvalidValue("upper", upper);

        this.lowerBoundVerify.execute(lower);
        this.upperBoundVerify.execute(upper);
        Verifier.requireFloatLessThanInclusive(getTitle(), "lower", lower, "upper", upper);

        return new FloatRangePreset(lower, upper);
    }

    private void verifyInvalidValue(String term, float value) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            String title = VerifierLogic.wrapTitle(getTitle());
            throw new IllegalArgumentException(title + term + "(" + value + ") Invalid");
        }
    }

}
