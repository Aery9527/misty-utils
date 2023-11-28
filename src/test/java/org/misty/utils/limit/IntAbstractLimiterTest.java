package org.misty.utils.limit;

import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.Verifier;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class IntAbstractLimiterTest {

    public void teset_build(Consumer<IntLimiterBuilder> setting) {
        int min = 1;
        int max = 3;
        int initValue = 2;

        String term = "kerker";
        IntLimiterBuilder limiterBuilder = Limiter.intLimiterBuilder(term)
                .giveMinLimit(min, true)
                .giveMaxLimit(max, true);
        setting.accept(limiterBuilder);

        IntLimiter limiter = limiterBuilder.build(initValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue);

        AssertionsEx.assertThrown(() -> limiterBuilder.build(0))
                .hasMessage(term + " " + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, Limiter.ErrorMsgFormat.SET_TERM, 0, min, max))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void test_set(BiFunction<IntLimitVerifierHandler, Integer, IntLimiter> setting) {
        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler("kerker", null, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
                if (target == 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        int initValue = 2;
        IntLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        int setValue = 1;
        limiter.set(setValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.set(0))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);
    }

    public void test_plus(BiFunction<IntLimitVerifierHandler, Integer, IntLimiter> setting) {
        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler("kerker", null, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                if (result < 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        int initValue = 0;
        IntLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        int plusValue = 1;
        int result = limiter.plus(plusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
        AssertionsEx.assertThat(result).isEqualTo(initValue + plusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.plus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
    }

    public void test_minus(BiFunction<IntLimitVerifierHandler, Integer, IntLimiter> setting) {
        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler("kerker", null, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                if (result > 0) {
                    throw new TestRuntimeException("");
                }
            }
        });

        int initValue = 0;
        IntLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        int minusValue = 1;
        limiter.minus(minusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.minus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);
    }

    public void test_increment(BiFunction<IntLimitVerifierHandler, Integer, IntLimiter> setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler("kerker", null, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                AssertionsEx.assertThat(plus).isEqualTo(1);
                checkPoint.set(true);
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        int initValue = 0;
        IntLimiter limiter = setting.apply(limitVerifierHandler, initValue);
        limiter.increment();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

    public void test_decrement(BiFunction<IntLimitVerifierHandler, Integer, IntLimiter> setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        IntLimitVerifierHandler limitVerifierHandler = new IntLimitVerifierHandler("kerker", null, new IntLimitVerifier() {
            @Override
            public void verifySet(int target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(int target, int plus, int result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(int target, int minus, int result) throws RuntimeException {
                AssertionsEx.assertThat(minus).isEqualTo(1);
                checkPoint.set(true);
            }
        });

        int initValue = 0;
        IntLimiter limiter = setting.apply(limitVerifierHandler, initValue);
        limiter.decrement();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

}
