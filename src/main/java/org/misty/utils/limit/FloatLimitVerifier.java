package org.misty.utils.limit;

public interface FloatLimitVerifier extends LimitVerifier {

    interface SetVerifier {
        void verify(float target) throws RuntimeException;
    }

    interface OperationVerifier {
        void verify(float target, float operator, float result) throws RuntimeException;
    }

    void verifySet(float target) throws RuntimeException;

    void verifyPlus(float target, float plus, float result) throws RuntimeException;

    void verifyMinus(float target, float minus, float result) throws RuntimeException;

}
