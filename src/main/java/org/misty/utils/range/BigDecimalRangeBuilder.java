package org.misty.utils.range;

import org.misty.utils.verify.BigDecimalRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;

public class BigDecimalRangeBuilder extends BaseRangeBuilder<BigDecimalRangeBuilder> {

    public static final int DEFAULT_SCALE = 2;

    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    private int scale = DEFAULT_SCALE;

    private RoundingMode roundingMode = DEFAULT_ROUNDING_MODE;

    private Consumer<BigDecimal> lowerBoundVerify = value -> {
    };

    private Consumer<BigDecimal> upperBoundVerify = value -> {
    };

    public BigDecimalRangeBuilder(String title) {
        super(title);
    }

    public BigDecimalRangeBuilder giveScale(int scale, RoundingMode roundingMode) {
        BigDecimal.ONE.setScale(scale, roundingMode); // pre-check
        this.scale = scale;
        this.roundingMode = roundingMode;
        return this;
    }

    public BigDecimalRangeBuilder giveLowerBound(double min, double max) {
        return giveLowerBound(toBigDecimal(min), toBigDecimal(max));
    }

    public BigDecimalRangeBuilder giveLowerBound(BigDecimal min, BigDecimal max) {
        Verifier.requireBigDecimalLessThanInclusive(getTitle(), "lowerMin", min, "lowerMax", max);
        BigDecimalRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.lowerBoundVerify = value -> rangeVerifier.requireInclusive("lower", value);
        return this;
    }

    public BigDecimalRangeBuilder giveUpperBound(double min, double max) {
        return giveUpperBound(toBigDecimal(min), toBigDecimal(max));
    }

    public BigDecimalRangeBuilder giveUpperBound(BigDecimal min, BigDecimal max) {
        Verifier.requireBigDecimalLessThanInclusive(getTitle(), "upperMin", min, "upperMax", max);
        BigDecimalRangeVerifier rangeVerifier = Verifier.ofRange(getTitle(), min, max);
        this.upperBoundVerify = value -> rangeVerifier.requireInclusive("upper", value);
        return this;
    }

    public BigDecimalRange build(BigDecimal lower, BigDecimal upper) {
        this.lowerBoundVerify.accept(lower);
        this.upperBoundVerify.accept(upper);
        Verifier.requireBigDecimalLessThanInclusive(getTitle(), "lower", lower, "upper", upper);

        return new BigDecimalRangePreset(lower, upper, this.scale, this.roundingMode);
    }

    private BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(this.scale, this.roundingMode);
    }

}
