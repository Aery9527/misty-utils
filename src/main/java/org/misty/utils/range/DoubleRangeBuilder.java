package org.misty.utils.range;

import org.misty.utils.verify.DoubleRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.misty.utils.verify.VerifierLogic;

import java.util.function.DoubleConsumer;

public class DoubleRangeBuilder extends BaseRangeBuilder<DoubleRangeBuilder> {

    private DoubleConsumer lowerBoundVerify = value -> {
    };

    private DoubleConsumer upperBoundVerify = value -> {
    };

    public DoubleRangeBuilder(String title) {
        super(title);
    }

    public DoubleRangeBuilder giveLowerBound(double min, double max) {
        verifyInvalidValue("lowerMin", min);
        verifyInvalidValue("lowerMax", max);
        Verifier.requireDoubleLessThanInclusive(getTitle(), "lowerMin", min, "lowerMax", max);

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public DoubleRangeBuilder giveUpperBound(double min, double max) {
        verifyInvalidValue("upperMin", min);
        verifyInvalidValue("upperMax", max);
        Verifier.requireDoubleLessThanInclusive(getTitle(), "upperMin", min, "upperMax", max);

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public DoubleRange build(double lower, double upper) {
        verifyInvalidValue("lower", lower);
        verifyInvalidValue("upper", upper);

        this.lowerBoundVerify.accept(lower);
        this.upperBoundVerify.accept(upper);
        Verifier.requireDoubleLessThanInclusive(getTitle(), "lower", lower, "upper", upper);

        return new DoubleRangePreset(lower, upper);
    }

    private void verifyInvalidValue(String term, double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            String title = VerifierLogic.wrapTitle(getTitle());
            throw new IllegalArgumentException(title + term + "(" + value + ") Invalid");
        }
    }

}
