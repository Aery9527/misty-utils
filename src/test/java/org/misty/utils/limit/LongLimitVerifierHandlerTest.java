package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class LongLimitVerifierHandlerTest {

    @Test
    public void verifySet() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);

        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
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
        AtomicLong checkTarget = new AtomicLong(0);
        AtomicLong checkPlus = new AtomicLong(0);
        AtomicLong checkResult = new AtomicLong(0);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                checkPoint2.set(true);
                checkTarget.set(target);
                checkPlus.set(plus);
                checkResult.set(result);
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
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
        AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(1, Long.MAX_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, 1, "+", Long.MAX_VALUE, (1 + Long.MAX_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_LONG_MAX))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();

        resetCheckPoint.run();
        AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(-1, Long.MIN_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, -1, "+", Long.MIN_VALUE, (-1 + Long.MIN_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_LONG_MIN))
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
        AtomicLong checkTarget = new AtomicLong(0);
        AtomicLong checkMinus = new AtomicLong(0);
        AtomicLong checkResult = new AtomicLong(0);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
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
        AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(0, Long.MIN_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, 0, "-", Long.MIN_VALUE, (0 - Long.MIN_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_LONG_MAX))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();

        resetCheckPoint.run();
        AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(-2, Long.MAX_VALUE))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, -2, "-", Long.MAX_VALUE, (-2 - Long.MAX_VALUE) + Limiter.ErrorMsgFormat.OVERFLOW_LONG_MIN))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
    }

}
