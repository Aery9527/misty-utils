package org.misty.utils.limit;

import java.math.BigInteger;

public interface BigIntegerLimitVerifier extends LimitVerifier {

    interface SetVerifier {
        void verify(BigInteger target) throws RuntimeException;
    }

    interface OperationVerifier {
        void verify(BigInteger target, BigInteger operator, BigInteger result) throws RuntimeException;
    }

    void verifySet(BigInteger target) throws RuntimeException;

    void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException;

    void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException;

}
