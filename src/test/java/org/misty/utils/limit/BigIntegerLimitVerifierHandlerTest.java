package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BigIntegerLimitVerifierHandlerTest {

    @Test
    public void verifySet() {
        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);

        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        // verify invoke
        BigInteger target = BigInteger.valueOf(9527);
        limitVerifierHandler.verifySet(target);

        AssertionsEx.assertThat(checkPoint1.get()).isTrue();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
    }

    @Test
    public void verifyPlus() {
        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicReference<BigInteger> checkTarget = new AtomicReference<>();
        AtomicReference<BigInteger> checkPlus = new AtomicReference<>();
        AtomicReference<BigInteger> checkResult = new AtomicReference<>();

        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                checkPoint2.set(true);
                checkTarget.set(target);
                checkPlus.set(plus);
                checkResult.set(result);
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        // verify invoke
        BigInteger target = BigInteger.valueOf(9527);
        BigInteger plus = BigInteger.valueOf(5566);
        limitVerifierHandler.verifyPlus(target, plus);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isTrue();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(target);
        AssertionsEx.assertThat(checkPlus.get()).isEqualTo(plus);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(target.add(plus));
    }

    @Test
    public void verifyMinus() {
        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicReference<BigInteger> checkTarget = new AtomicReference<>();
        AtomicReference<BigInteger> checkMinus = new AtomicReference<>();
        AtomicReference<BigInteger> checkResult = new AtomicReference<>();

        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                checkPoint3.set(true);
                checkTarget.set(target);
                checkMinus.set(minus);
                checkResult.set(result);
            }
        });

        // verify invoke
        BigInteger target = BigInteger.valueOf(9527);
        BigInteger minus = BigInteger.valueOf(5566);
        limitVerifierHandler.verifyMinus(target, minus);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isTrue();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(target);
        AssertionsEx.assertThat(checkMinus.get()).isEqualTo(minus);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(target.subtract(minus));
    }

}
