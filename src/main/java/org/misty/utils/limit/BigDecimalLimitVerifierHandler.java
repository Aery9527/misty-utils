package org.misty.utils.limit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalLimitVerifierHandler implements LimitVerifier {

    private final int scale;

    private final RoundingMode roundingMode;

    private final BigDecimalLimitVerifier.SetVerifier setVerifier;

    private final BigDecimalLimitVerifier.OperationVerifier plusVerifier;

    private final BigDecimalLimitVerifier.OperationVerifier minusVerifier;

    public BigDecimalLimitVerifierHandler(int scale,
                                          RoundingMode roundingMode,
                                          BigDecimalLimitVerifier limitVerifier) {
        this(scale, roundingMode, limitVerifier::verifySet, limitVerifier::verifyPlus, limitVerifier::verifyMinus);
    }

    public BigDecimalLimitVerifierHandler(int scale,
                                          RoundingMode roundingMode,
                                          BigDecimalLimitVerifier.SetVerifier setVerifier,
                                          BigDecimalLimitVerifier.OperationVerifier plusVerifier,
                                          BigDecimalLimitVerifier.OperationVerifier minusVerifier) {
        this.scale = scale;
        this.roundingMode = roundingMode;
        this.setVerifier = setVerifier;
        this.plusVerifier = plusVerifier;
        this.minusVerifier = minusVerifier;
    }

    public BigDecimal verifySet(BigDecimal target) {
        target = setScale(target);
        this.setVerifier.verify(target);
        return target;
    }

    public BigDecimal verifyPlus(BigDecimal target, BigDecimal plus) {
        BigDecimal result = setScale(target.add(plus));
        this.plusVerifier.verify(target, plus, result);
        return result;
    }

    public BigDecimal verifyMinus(BigDecimal target, BigDecimal minus) {
        BigDecimal result = setScale(target.subtract(minus));
        this.minusVerifier.verify(target, minus, result);
        return result;
    }

    public BigDecimal setScale(BigDecimal target) {
        return target.setScale(this.scale, this.roundingMode);
    }

    public int getScale() {
        return scale;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

}
