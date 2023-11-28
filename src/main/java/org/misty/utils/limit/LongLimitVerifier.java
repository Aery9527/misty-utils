package org.misty.utils.limit;

public interface LongLimitVerifier extends LimitVerifier {

    interface SetVerifier {
        void verify(long target) throws RuntimeException;
    }

    interface OperationVerifier {
        void verify(long target, long operator, long result) throws RuntimeException;
    }

    void verifySet(long target) throws RuntimeException;

    void verifyPlus(long target, long plus, long result) throws RuntimeException;

    void verifyMinus(long target, long minus, long result) throws RuntimeException;

}
