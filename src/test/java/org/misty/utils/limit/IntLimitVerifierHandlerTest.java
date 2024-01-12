package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class IntLimitVerifierHandlerTest {

    @Test
    public void verifySet() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);

        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        // verify invoke
        limitVerifierHandler.verifySet(0);

        AssertionsEx.assertThat(checkPoint1.get()).isTrue();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
    }

    @Test
    public void verifyPlus() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicInteger checkTarget = new AtomicInteger(0);
        AtomicInteger checkPlus = new AtomicInteger(0);
        AtomicInteger checkResult = new AtomicInteger(0);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                checkPoint2.set(true);
                checkTarget.set(target);
                checkPlus.set(plus);
                checkResult.set(result);
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        // verify invoke
        resetCheckPoint.run();
        limitVerifierHandler.verifyPlus(9527, 5566);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isTrue();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(9527);
        AssertionsEx.assertThat(checkPlus.get()).isEqualTo(5566);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(9527 + 5566);

        // verify overflow
        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyPlus(1, Integer.MAX_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, 1, "+", Integer.MAX_VALUE, (1 + Integer.MAX_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_INTEGER_MAX))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();

        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyPlus(-1, Integer.MIN_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, -1, "+", Integer.MIN_VALUE, (-1 + Integer.MIN_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_INTEGER_MIN))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
    }

    @Test
    public void verifyMinus() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicInteger checkTarget = new AtomicInteger(0);
        AtomicInteger checkMinus = new AtomicInteger(0);
        AtomicInteger checkResult = new AtomicInteger(0);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                checkPoint3.set(true);
                checkTarget.set(target);
                checkMinus.set(minus);
                checkResult.set(result);
            }
        });

        // verify invoke
        resetCheckPoint.run();
        limitVerifierHandler.verifyMinus(9527, 5566);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isTrue();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(9527);
        AssertionsEx.assertThat(checkMinus.get()).isEqualTo(5566);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(9527 - 5566);

        // verify overflow
        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyMinus(0, Integer.MIN_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, 0, "-", Integer.MIN_VALUE, (0 - Integer.MIN_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_INTEGER_MAX))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();

        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyMinus(-2, Integer.MAX_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, -2, "-", Integer.MAX_VALUE, (-2 - Integer.MAX_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_INTEGER_MIN))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
    }

}
