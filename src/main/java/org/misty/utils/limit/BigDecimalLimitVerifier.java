package org.misty.utils.limit;

import java.math.BigDecimal;

public interface BigDecimalLimitVerifier extends LimitVerifier {

    interface SetVerifier {
        void verify(BigDecimal target) throws RuntimeException;
    }

    interface OperationVerifier {
        void verify(BigDecimal target, BigDecimal operator, BigDecimal result) throws RuntimeException;
    }

    void verifySet(BigDecimal target) throws RuntimeException;

    void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException;

    void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException;

}
