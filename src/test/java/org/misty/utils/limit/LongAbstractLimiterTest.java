package org.misty.utils.limit;

import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.Verifier;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class LongAbstractLimiterTest {

    public void teset_build(Consumer<LongLimiterBuilder> setting) {
        long min = 1;
        long max = 3;
        long initValue = 2;

        String term = "kerker";
        LongLimiterBuilder limiterBuilder = Limiter.longLimiterBuilder(term)
                .giveMinLimit(min, true)
                .giveMaxLimit(max, true);
        setting.accept(limiterBuilder);

        LongLimiter limiter = limiterBuilder.build(initValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue);

        AssertionsEx.assertThrown(() -> limiterBuilder.build(0l))
                .hasMessage(term + " " + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, Limiter.ErrorMsgFormat.SET_TERM, 0, min, max))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void test_set(BiFunction<LongLimitVerifierHandler, Long, LongLimiter> setting) {
        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler("kerker", null, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
                if (target == 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        long initValue = 2;
        LongLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        long setValue = 1;
        limiter.set(setValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.set(0))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);
    }

    public void test_plus(BiFunction<LongLimitVerifierHandler, Long, LongLimiter> setting) {
        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler("kerker", null, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                if (result < 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        long initValue = 0;
        LongLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        long plusValue = 1;
        long result = limiter.plus(plusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
        AssertionsEx.assertThat(result).isEqualTo(initValue + plusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.plus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue + plusValue);
    }

    public void test_minus(BiFunction<LongLimitVerifierHandler, Long, LongLimiter> setting) {
        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler("kerker", null, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
                if (result > 0) {
                    throw new TestRuntimeException("");
                }
            }
        });

        long initValue = 0;
        LongLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        long minusValue = 1;
        limiter.minus(minusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.minus(-2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue - minusValue);
    }

    public void test_increment(BiFunction<LongLimitVerifierHandler, Long, LongLimiter> setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler("kerker", null, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                AssertionsEx.assertThat(plus).isEqualTo(1);
                checkPoint.set(true);
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        long initValue = 0;
        LongLimiter limiter = setting.apply(limitVerifierHandler, initValue);
        limiter.increment();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

    public void test_decrement(BiFunction<LongLimitVerifierHandler, Long, LongLimiter> setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        LongLimitVerifierHandler limitVerifierHandler = new LongLimitVerifierHandler("kerker", null, new LongLimitVerifier() {
            @Override
            public void verifySet(long target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(long target, long plus, long result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(long target, long minus, long result) throws RuntimeException {
                AssertionsEx.assertThat(minus).isEqualTo(1);
                checkPoint.set(true);
            }
        });

        long initValue = 0;
        LongLimiter limiter = setting.apply(limitVerifierHandler, initValue);
        limiter.decrement();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

}
