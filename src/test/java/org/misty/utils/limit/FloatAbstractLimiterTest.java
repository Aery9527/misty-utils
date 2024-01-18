package org.misty.utils.limit;

import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.Verifier;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class FloatAbstractLimiterTest {

    public interface TestInterface {
        FloatLimiter setting(FloatLimitVerifierHandler verifierHandler, float min, float max, float initValue);
    }

    private final FloatLimitVerifierHandler mockVerifierHandler = Mockito.mock(FloatLimitVerifierHandler.class);

    private final float min = 2266;

    private final float max = 9527;

    public void teset_build(Consumer<FloatLimiterBuilder> setting) {
        float min = 1;
        float max = 3;
        float initValue = 2;

        String term = "kerker";
        FloatLimiterBuilder limiterBuilder = Limiter.floatLimiterBuilder(term)
                .giveMinLimit(min, true)
                .giveMaxLimit(max, true);
        setting.accept(limiterBuilder);

        FloatLimiter limiter = limiterBuilder.build(initValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue);

        AssertionsEx.awareThrown(() -> limiterBuilder.build(0f))
                .hasMessage(term + " " + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, Limiter.ErrorMsgFormat.SET_TERM, 0f, min, max))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void test_min_max(TestInterface setting) {
        FloatLimiter limiter = setting.setting(this.mockVerifierHandler, this.min, this.max, 5566);
        AssertionsEx.assertThat(limiter.getMin().get()).isEqualTo(this.min);
        AssertionsEx.assertThat(limiter.getMax().get()).isEqualTo(this.max);
    }

    public void test_set(TestInterface setting) {
        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler("kerker", null, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
                if (target == 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        }, false);

        float initValue = 2;
        FloatLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        float setValue = 1;
        limiter.set(setValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.set(0))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);
    }

    public void test_plus(TestInterface setting) {
        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler("kerker", null, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                if (result < 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        }, false);

        float initValue = 0;
        FloatLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        float plusValue = 1;
        float result = limiter.plus(plusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
        AssertionsEx.assertThat(result).isEqualTo(initValue + plusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.plus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
    }

    public void test_minus(TestInterface setting) {
        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler("kerker", null, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                if (result > 0) {
                    throw new TestRuntimeException("");
                }
            }
        }, false);

        float initValue = 0;
        FloatLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        float minusValue = 1;
        limiter.minus(minusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.minus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);
    }

    public void test_increment(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler("kerker", null, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                AssertionsEx.assertThat(plus).isEqualTo(1);
                checkPoint.set(true);
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        }, false);

        float initValue = 0;
        FloatLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.increment();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

    public void test_decrement(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        FloatLimitVerifierHandler limitVerifierHandler = new FloatLimitVerifierHandler("kerker", null, new FloatLimitVerifier() {
            @Override
            public void verifySet(float target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(float target, float plus, float result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(float target, float minus, float result) throws RuntimeException {
                AssertionsEx.assertThat(minus).isEqualTo(1);
                checkPoint.set(true);
            }
        }, false);

        float initValue = 0;
        FloatLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.decrement();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

}
