package org.misty.utils.limit;

public interface DoubleLimitVerifier extends LimitVerifier {

    interface SetVerifier {
        void verify(double target) throws RuntimeException;
    }

    interface OperationVerifier {
        void verify(double target, double operator, double result) throws RuntimeException;
    }

    void verifySet(double target) throws RuntimeException;

    void verifyPlus(double target, double plus, double result) throws RuntimeException;

    void verifyMinus(double target, double minus, double result) throws RuntimeException;

}
