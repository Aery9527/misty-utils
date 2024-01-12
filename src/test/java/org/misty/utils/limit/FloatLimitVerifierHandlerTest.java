package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.combinatorics.Combinations;

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

        Consumer<Boolean> test = acceptUnlimited -> {
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
            }, acceptUnlimited);

            // verify invoke
            resetCheckPoint.run();

            limitVerifierHandler.verifySet(0);

            AssertionsEx.assertThat(checkPoint1.get()).isTrue();
            AssertionsEx.assertThat(checkPoint2.get()).isFalse();
            AssertionsEx.assertThat(checkPoint3.get()).isFalse();

            // verify acceptUnlimited
            Stream.of(Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY).forEach(target -> {
                resetCheckPoint.run();

                if (acceptUnlimited) {
                    limitVerifierHandler.verifySet(target);
                } else {
                    AssertionsEx.awareThrown(() -> limitVerifierHandler.verifySet(target))
                            .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN, targetTerm, target))
                            .isInstanceOf(TestRuntimeException.class);
                }

                AssertionsEx.assertThat(checkPoint1.get()).isEqualTo(acceptUnlimited);
                AssertionsEx.assertThat(checkPoint2.get()).isFalse();
                AssertionsEx.assertThat(checkPoint3.get()).isFalse();
            });
        };

        test.accept(true);
        test.accept(false);
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

        Consumer<Boolean> test = acceptUnlimited -> {
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
            }, acceptUnlimited);

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
            Combinations<Float> combinations = Combinations.of(0f, Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
            combinations.foreach(2, true, combination -> { // filter
                return combination.get(0).content != 0f || combination.get(1).content != 0f;
            }, combination -> { // test
                resetCheckPoint.run();

                float a = combination.get(0);
                float b = combination.get(1);

                if (acceptUnlimited) {
                    limitVerifierHandler.verifyPlus(a, b);
                    limitVerifierHandler.verifyPlus(b, a);
                } else {
                    AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyPlus(a, b))
                            .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, targetTerm, a, "plus", b))
                            .isInstanceOf(TestRuntimeException.class);
                    AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyPlus(b, a))
                            .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, targetTerm, b, "plus", a))
                            .isInstanceOf(TestRuntimeException.class);
                }

                AssertionsEx.assertThat(checkPoint1.get()).isFalse();
                AssertionsEx.assertThat(checkPoint2.get()).isEqualTo(acceptUnlimited);
                AssertionsEx.assertThat(checkPoint3.get()).isFalse();
            });
        };

        test.accept(true);
        test.accept(false);
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


        Consumer<Boolean> test = acceptUnlimited -> {
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
            }, acceptUnlimited);

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
            Combinations<Float> combinations = Combinations.of(0f, Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
            combinations.foreach(2, true, combination -> { // filter
                return combination.get(0).content != 0f || combination.get(1).content != 0f;
            }, combination -> { // test
                resetCheckPoint.run();

                float a = combination.get(0);
                float b = combination.get(1);

                if (acceptUnlimited) {
                    limitVerifierHandler.verifyMinus(a, b);
                    limitVerifierHandler.verifyMinus(b, a);
                } else {
                    AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyMinus(a, b))
                            .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, targetTerm, a, "minus", b))
                            .isInstanceOf(TestRuntimeException.class);
                    AssertionsEx.awareThrown(() -> limitVerifierHandler.verifyMinus(b, a))
                            .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, targetTerm, b, "minus", a))
                            .isInstanceOf(TestRuntimeException.class);
                }

                AssertionsEx.assertThat(checkPoint1.get()).isFalse();
                AssertionsEx.assertThat(checkPoint2.get()).isFalse();
                AssertionsEx.assertThat(checkPoint3.get()).isEqualTo(acceptUnlimited);
            });
        };

        test.accept(true);
        test.accept(false);
    }

}
