package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class DoubleLimitVerifierHandlerTest {

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

        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
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
        AtomicReference<Double> checkTarget = new AtomicReference<>(0d);
        AtomicReference<Double> checkPlus = new AtomicReference<>(0d);
        AtomicReference<Double> checkResult = new AtomicReference<>(0d);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                checkPoint2.set(true);
                checkTarget.set(target);
                checkPlus.set(plus);
                checkResult.set(result);
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
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

        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(0f, Double.NaN))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "plus", Double.NaN))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(0f, Double.POSITIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "plus", Double.POSITIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(0f, Double.NEGATIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "plus", Double.NEGATIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(Double.NaN, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Double.NaN, "plus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(Double.POSITIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Double.POSITIVE_INFINITY, "plus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(Double.NEGATIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Double.NEGATIVE_INFINITY, "plus", 0f))
                .isInstanceOf(TestRuntimeException.class));
    }

    @Test
    public void verifyMinus() {
        String targetTerm = "kerker";

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        AtomicBoolean checkPoint2 = new AtomicBoolean(false);
        AtomicBoolean checkPoint3 = new AtomicBoolean(false);
        AtomicReference<Double> checkTarget = new AtomicReference<>(0d);
        AtomicReference<Double> checkMinus = new AtomicReference<>(0d);
        AtomicReference<Double> checkResult = new AtomicReference<>(0d);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler(targetTerm, errorMsg -> { // limiterThrown
            throw new TestRuntimeException(errorMsg);
        }, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
                checkPoint1.set(true);
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                checkPoint2.set(true);
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
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

        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(0f, Double.NaN))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "minus", Double.NaN))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(0f, Double.POSITIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "minus", Double.POSITIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(0f, Double.NEGATIVE_INFINITY))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, 0f, "minus", Double.NEGATIVE_INFINITY))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(Double.NaN, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Double.NaN, "minus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(Double.POSITIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Double.POSITIVE_INFINITY, "minus", 0f))
                .isInstanceOf(TestRuntimeException.class));
        acceptUnlimitedTest.accept(() -> AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(Double.NEGATIVE_INFINITY, 0f))
                .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, Double.NEGATIVE_INFINITY, "minus", 0f))
                .isInstanceOf(TestRuntimeException.class));
    }

}
