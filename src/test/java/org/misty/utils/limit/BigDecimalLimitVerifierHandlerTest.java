package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BigDecimalLimitVerifierHandlerTest {

    @Test
    public void verifySet() {
        int scale = 4;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        AtomicReference<BigDecimal> checkPoint0 = new AtomicReference<>();
        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);

        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(scale, roundingMode, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
                checkPoint0.set(target);
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        BigDecimal target = BigDecimal.valueOf(0.123456789);
        limitVerifierHandler.verifySet(target);

        AssertionsEx.assertThat(checkPoint0.get()).isEqualTo(target.setScale(scale, roundingMode)); // verify scale
        AssertionsEx.assertThat(checkPoint1.get()).isTrue(); // verify invoke
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
    }

    @Test
    public void verifyPlus() {
        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicReference<BigDecimal> checkTarget = new AtomicReference<>();
        AtomicReference<BigDecimal> checkPlus = new AtomicReference<>();
        AtomicReference<BigDecimal> checkResult = new AtomicReference<>();

        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(scale, roundingMode, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                checkPoint2.set(true);
                checkTarget.set(target);
                checkPlus.set(plus);
                checkResult.set(result);
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        BigDecimal target = BigDecimal.valueOf(Math.random() * 10);
        BigDecimal plus = BigDecimal.valueOf(Math.random() * 10);
        limitVerifierHandler.verifyPlus(target, plus);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isTrue(); // verify invoke
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(target);
        AssertionsEx.assertThat(checkPlus.get()).isEqualTo(plus);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(target.add(plus).setScale(scale, roundingMode)); // verify scale
    }

    @Test
    public void verifyMinus() {
        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicReference<BigDecimal> checkTarget = new AtomicReference<>();
        AtomicReference<BigDecimal> checkMinus = new AtomicReference<>();
        AtomicReference<BigDecimal> checkResult = new AtomicReference<>();

        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(scale, roundingMode, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                checkPoint3.set(true);
                checkTarget.set(target);
                checkMinus.set(minus);
                checkResult.set(result);
            }
        });

        BigDecimal target = BigDecimal.valueOf(Math.random() * 10);
        BigDecimal minus = BigDecimal.valueOf(Math.random() * 10);
        limitVerifierHandler.verifyMinus(target, minus);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isTrue(); // verify invoke
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(target);
        AssertionsEx.assertThat(checkMinus.get()).isEqualTo(minus);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(target.subtract(minus).setScale(scale, roundingMode)); // verify scale
    }

}
