package org.misty.utils.limit;

public interface ShortLimitVerifier extends LimitVerifier {

    interface SetVerifier {
        void verify(short target) throws RuntimeException;
    }

    interface OperationVerifier {
        void verify(short target, short operator, short result) throws RuntimeException;
    }

    void verifySet(short target) throws RuntimeException;

    void verifyPlus(short target, short plus, short result) throws RuntimeException;

    void verifyMinus(short target, short minus, short result) throws RuntimeException;

}
