package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.combinatorics.Combinations;

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

        Consumer<Boolean> test = acceptUnlimited -> {
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
            }, acceptUnlimited);

            // verify invoke
            resetCheckPoint.run();

            limitVerifierHandler.verifySet(0);

            AssertionsEx.assertThat(checkPoint1.get()).isTrue();
            AssertionsEx.assertThat(checkPoint2.get()).isFalse();
            AssertionsEx.assertThat(checkPoint3.get()).isFalse();

            // verify acceptUnlimited
            Stream.of(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY).forEach(target -> {
                resetCheckPoint.run();

                if (acceptUnlimited) {
                    limitVerifierHandler.verifySet(target);
                } else {
                    AssertionsEx.assertThrown(() -> limitVerifierHandler.verifySet(target))
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
        AtomicReference<Double> checkTarget = new AtomicReference<>(0d);
        AtomicReference<Double> checkPlus = new AtomicReference<>(0d);
        AtomicReference<Double> checkResult = new AtomicReference<>(0d);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };

        Consumer<Boolean> test = acceptUnlimited -> {
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
            Combinations<Double> combinations = Combinations.of(0d, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            combinations.foreach(2, true, combination -> { // filter
                return combination.get(0).content != 0f || combination.get(1).content != 0f;
            }, combination -> { // test
                resetCheckPoint.run();

                double a = combination.get(0);
                double b = combination.get(1);

                if (acceptUnlimited) {
                    limitVerifierHandler.verifyPlus(a, b);
                    limitVerifierHandler.verifyPlus(b, a);
                } else {
                    AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(a, b))
                            .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, targetTerm, a, "plus", b))
                            .isInstanceOf(TestRuntimeException.class);
                    AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyPlus(b, a))
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
        AtomicReference<Double> checkTarget = new AtomicReference<>(0d);
        AtomicReference<Double> checkMinus = new AtomicReference<>(0d);
        AtomicReference<Double> checkResult = new AtomicReference<>(0d);

        Runnable resetCheckPoint = () -> {
            checkPoint1.set(false);
            checkPoint2.set(false);
            checkPoint3.set(false);
        };


        Consumer<Boolean> test = acceptUnlimited -> {
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
            Combinations<Double> combinations = Combinations.of(0d, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            combinations.foreach(2, true, combination -> { // filter
                return combination.get(0).content != 0f || combination.get(1).content != 0f;
            }, combination -> { // test
                resetCheckPoint.run();

                double a = combination.get(0);
                double b = combination.get(1);

                if (acceptUnlimited) {
                    limitVerifierHandler.verifyMinus(a, b);
                    limitVerifierHandler.verifyMinus(b, a);
                } else {
                    AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(a, b))
                            .hasMessage(String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, targetTerm, a, "minus", b))
                            .isInstanceOf(TestRuntimeException.class);
                    AssertionsEx.assertThrown(() -> limitVerifierHandler.verifyMinus(b, a))
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
