package org.misty.utils.limit;

import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.Verifier;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class DoubleAbstractLimiterTest {

    public interface TestInterface {
        DoubleLimiter setting(DoubleLimitVerifierHandler verifierHandler, double min, double max, double initValue);
    }

    private final DoubleLimitVerifierHandler mockVerifierHandler = Mockito.mock(DoubleLimitVerifierHandler.class);

    private final double min = 2266;

    private final double max = 9527;

    public void teset_build(Consumer<DoubleLimiterBuilder> setting) {
        double min = 1;
        double max = 3;
        double initValue = 2;

        String term = "kerker";
        DoubleLimiterBuilder limiterBuilder = Limiter.doubleLimiterBuilder(term)
                .giveMinLimit(min, true)
                .giveMaxLimit(max, true);
        setting.accept(limiterBuilder);

        DoubleLimiter limiter = limiterBuilder.build(initValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue);

        AssertionsEx.awareThrown(() -> limiterBuilder.build(0d))
                .hasMessage(term + " " + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, Limiter.ErrorMsgFormat.SET_TERM, 0d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void test_min_max(TestInterface setting) {
        DoubleLimiter limiter = setting.setting(this.mockVerifierHandler, this.min, this.max, 5566);
        AssertionsEx.assertThat(limiter.getMin()).isEqualTo(this.min);
        AssertionsEx.assertThat(limiter.getMax()).isEqualTo(this.max);
    }

    public void test_set(TestInterface setting) {
        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler("kerker", null, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
                if (target == 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        }, false);

        double initValue = 2;
        DoubleLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        double setValue = 1;
        limiter.set(setValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.set(0))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);
    }

    public void test_plus(TestInterface setting) {
        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler("kerker", null, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                if (result < 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        }, false);

        double initValue = 0;
        DoubleLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        double plusValue = 1;
        double result = limiter.plus(plusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
        AssertionsEx.assertThat(result).isEqualTo(initValue + plusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.plus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
    }

    public void test_minus(TestInterface setting) {
        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler("kerker", null, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
                if (result > 0) {
                    throw new TestRuntimeException("");
                }
            }
        }, false);

        double initValue = 0;
        DoubleLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        double minusValue = 1;
        limiter.minus(minusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.minus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);
    }

    public void test_increment(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler("kerker", null, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                AssertionsEx.assertThat(plus).isEqualTo(1);
                checkPoint.set(true);
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        }, false);

        double initValue = 0;
        DoubleLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.increment();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

    public void test_decrement(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        DoubleLimitVerifierHandler limitVerifierHandler = new DoubleLimitVerifierHandler("kerker", null, new DoubleLimitVerifier() {
            @Override
            public void verifySet(double target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(double target, double plus, double result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(double target, double minus, double result) throws RuntimeException {
                AssertionsEx.assertThat(minus).isEqualTo(1);
                checkPoint.set(true);
            }
        }, false);

        double initValue = 0;
        DoubleLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.decrement();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

}
