package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ShortLimitVerifierHandlerTest {

    @Test
    public void verifySet() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);

        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        // verify invoke
        limitVerifierHandler.verifySet((short) 0);

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
        AtomicReference<Short> checkTarget = new AtomicReference<>((short) 0);
        AtomicReference<Short> checkPlus = new AtomicReference<>((short) 0);
        AtomicReference<Short> checkResult = new AtomicReference<>((short) 0);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                checkPoint2.set(true);
                checkTarget.set(target);
                checkPlus.set(plus);
                checkResult.set(result);
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                checkPoint3.set(true);
            }
        });

        // verify invoke
        resetCheckPoint.run();
        limitVerifierHandler.verifyPlus((short) 9527, (short) 5566);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isTrue();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo((short) 9527);
        AssertionsEx.assertThat(checkPlus.get()).isEqualTo((short) 5566);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo((short) (9527 + 5566));

        // verify overflow
        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyPlus((short) 1, Short.MAX_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, 1, "+", Short.MAX_VALUE, (short) (1 + Short.MAX_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_SHORT_MAX))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();

        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyPlus((short) -1, Short.MIN_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, -1, "+", Short.MIN_VALUE, (short) (-1 + Short.MIN_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_SHORT_MIN))
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
        AtomicReference<Short> checkTarget = new AtomicReference<>((short) 0);
        AtomicReference<Short> checkMinus = new AtomicReference<>((short) 0);
        AtomicReference<Short> checkResult = new AtomicReference<>((short) 0);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                checkPoint3.set(true);
                checkTarget.set(target);
                checkMinus.set(minus);
                checkResult.set(result);
            }
        });

        // verify invoke
        resetCheckPoint.run();
        limitVerifierHandler.verifyMinus((short) 9527, (short) 5566);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isTrue();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo((short) 9527);
        AssertionsEx.assertThat(checkMinus.get()).isEqualTo((short) 5566);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo((short) (9527 - 5566));

        // verify overflow
        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyMinus((short) 0, Short.MIN_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, 0, "-", Short.MIN_VALUE, (short) (0 - Short.MIN_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_SHORT_MAX))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();

        resetCheckPoint.run();
        AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyMinus((short) -2, Short.MAX_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, -2, "-", Short.MAX_VALUE, (short) (-2 - Short.MAX_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_SHORT_MIN))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
    }

}
