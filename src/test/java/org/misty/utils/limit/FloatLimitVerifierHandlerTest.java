package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FloatLimitVerifierHandlerTest {

    @Test
    public void verifySet() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                checkPoint3.set(true);
            }
        }, false);

        // verify invoke
        limitVerifierHandler.verifySet(0);

        AssertionsEx.assertThat(checkPoint1.get()).isTrue();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();

        // verify acceptUnlimited
        Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY).forEach(target -> {
            resetCheckPoint.run();

            AssertionsEx.assertThrown(() -> limitVerifierHandler.verifySet(target))
                    .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN, target, ""))
                    .isInstanceOf(TestRuntimeException.class);

            AssertionsEx.assertThat(checkPoint1.get()).isFalse();
            AssertionsEx.assertThat(checkPoint2.get()).isFalse();
            AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        });
    }

    @Test
    public void verifyPlus() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicReference<Float> checkTarget = new AtomicReference<>(0f);
        AtomicReference<Float> checkPlus = new AtomicReference<>(0f);
        AtomicReference<Float> checkResult = new AtomicReference<>(0f);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                checkPoint2.set(true);
                checkTarget.set(target);
                checkPlus.set(plus);
                checkResult.set(result);
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                checkPoint3.set(true);
            }
        }, false);

        // verify invoke
        resetCheckPoint.run();
        limitVerifierHandler.verifyPlus(9527, 5566);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isTrue();
        AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(9527f);
        AssertionsEx.assertThat(checkPlus.get()).isEqualTo(5566f);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(9527f + 5566f);

        // verify acceptUnlimited
        Consumer<Runnable> acceptUnlimitedTest = (action) -> {
            resetCheckPoint.run();

            action.run();

            AssertionsEx.assertThat(checkPoint1.get()).isFalse();
            AssertionsEx.assertThat(checkPoint2.get()).isFalse();
            AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        };

        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(0f, Float.NaN))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "plus", Float.NaN))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(0f, Float.POSITIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "plus", Float.POSITIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(0f, Float.NEGATIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "plus", Float.NEGATIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(Float.NaN, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Float.NaN, "plus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(Float.POSITIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Float.POSITIVE_INFINITY, "plus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(Float.NEGATIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Float.NEGATIVE_INFINITY, "plus", 0f))
                .isInstanceOf(TestRuntimeException.class));
    }

    @Test
    public void verifyMinus() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicReference<Float> checkTarget = new AtomicReference<>(0f);
        AtomicReference<Float> checkMinus = new AtomicReference<>(0f);
        AtomicReference<Float> checkResult = new AtomicReference<>(0f);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                checkPoint3.set(true);
                checkTarget.set(target);
                checkMinus.set(minus);
                checkResult.set(result);
            }
        }, false);

        // verify invoke
        resetCheckPoint.run();
        limitVerifierHandler.verifyMinus(9527, 5566);

        AssertionsEx.assertThat(checkPoint1.get()).isFalse();
        AssertionsEx.assertThat(checkPoint2.get()).isFalse();
        AssertionsEx.assertThat(checkPoint3.get()).isTrue();
        AssertionsEx.assertThat(checkTarget.get()).isEqualTo(9527);
        AssertionsEx.assertThat(checkMinus.get()).isEqualTo(5566);
        AssertionsEx.assertThat(checkResult.get()).isEqualTo(9527 - 5566);

        // verify acceptUnlimited
        Consumer<Runnable> acceptUnlimitedTest = (action) -> {
            resetCheckPoint.run();

            action.run();

            AssertionsEx.assertThat(checkPoint1.get()).isFalse();
            AssertionsEx.assertThat(checkPoint2.get()).isFalse();
            AssertionsEx.assertThat(checkPoint3.get()).isFalse();
        };

        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(0f, Float.NaN))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "minus", Float.NaN))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(0f, Float.POSITIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "minus", Float.POSITIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(0f, Float.NEGATIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "minus", Float.NEGATIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(Float.NaN, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Float.NaN, "minus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(Float.POSITIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Float.POSITIVE_INFINITY, "minus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(Float.NEGATIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Float.NEGATIVE_INFINITY, "minus", 0f))
                .isInstanceOf(TestRuntimeException.class));
    }

}
