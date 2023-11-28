package org.misty.utils.limit;

public interface IntLimitVerifier extends LimitVerifier {

    interface SetVerifier {
        void verify(int target) throws RuntimeException;
    }

    interface OperationVerifier {
        void verify(int target, int operator, int result) throws RuntimeException;
    }

    void verifySet(int target) throws RuntimeException;

    void verifyPlus(int target, int plus, int result) throws RuntimeException;

    void verifyMinus(int target, int minus, int result) throws RuntimeException;

}
