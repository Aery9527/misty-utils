package org.misty.utils.limit;

import java.math.BigInteger;

public class BigIntegerLimitVerifierHandler implements LimitVerifier {

    private final BigIntegerLimitVerifier.SetVerifier setVerifier;

    private final BigIntegerLimitVerifier.OperationVerifier plusVerifier;

    private final BigIntegerLimitVerifier.OperationVerifier minusVerifier;

    public BigIntegerLimitVerifierHandler(BigIntegerLimitVerifier limitVerifier) {
        this(limitVerifier::verifySet, limitVerifier::verifyPlus, limitVerifier::verifyMinus);
    }

    public BigIntegerLimitVerifierHandler(BigIntegerLimitVerifier.SetVerifier setVerifier,
                                          BigIntegerLimitVerifier.OperationVerifier plusVerifier,
                                          BigIntegerLimitVerifier.OperationVerifier minusVerifier) {
        this.setVerifier = setVerifier;
        this.plusVerifier = plusVerifier;
        this.minusVerifier = minusVerifier;
    }

    public void verifySet(BigInteger target) {
        this.setVerifier.verify(target);
    }

    public BigInteger verifyPlus(BigInteger target, BigInteger plus) {
        BigInteger result = target.add(plus);
        this.plusVerifier.verify(target, plus, result);
        return result;
    }

    public BigInteger verifyMinus(BigInteger target, BigInteger minus) {
        BigInteger result = target.subtract(minus);
        this.minusVerifier.verify(target, minus, result);
        return result;
    }

}
