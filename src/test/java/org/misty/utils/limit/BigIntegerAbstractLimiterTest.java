package org.misty.utils.limit;

import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.Verifier;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class BigIntegerAbstractLimiterTest {

    private final BigInteger $0 = BigInteger.ZERO;

    private final BigInteger $1 = BigInteger.valueOf(1);

    private final BigInteger $2 = BigInteger.valueOf(2);

    private final BigInteger $3 = BigInteger.valueOf(3);

    public void teset_build(Consumer<BigIntegerLimiterBuilder> setting) {
        BigInteger min = $1;
        BigInteger max = $3;
        BigInteger initValue = $2;

        String term = "kerker";
        BigIntegerLimiterBuilder limiterBuilder = Limiter.bigIntegerBuilder(term)
                .giveMinLimit(min, true)
                .giveMaxLimit(max, true);
        setting.accept(limiterBuilder);

        BigIntegerLimiter limiter = limiterBuilder.build(initValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue);

        AssertionsEx.assertThrown(() -> limiterBuilder.build($0))
                .hasMessage(term + " " + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, Limiter.ErrorMsgFormat.SET_TERM, $0, min, max))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void test_set(BiFunction<BigIntegerLimitVerifierHandler, BigInteger, BigIntegerLimiter> setting) {
        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
                if (target.compareTo($0) == 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        BigInteger initValue = $2;
        BigIntegerLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        BigInteger setValue = $1;
        limiter.set(setValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.set($0))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);
    }

    public void test_plus(BiFunction<BigIntegerLimitVerifierHandler, BigInteger, BigIntegerLimiter> setting) {
        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                if (result.compareTo($0) < 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        BigInteger initValue = $0;
        BigIntegerLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        BigInteger plusValue = $1;
        BigInteger result = limiter.plus(plusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue.add(plusValue));
        AssertionsEx.assertThat(result).isEqualTo(initValue.add(plusValue));

        AssertionsEx.assertThatThrownBy(() -> limiter.plus($0.subtract($2)))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue.add(plusValue));
    }

    public void test_minus(BiFunction<BigIntegerLimitVerifierHandler, BigInteger, BigIntegerLimiter> setting) {
        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                if (result.compareTo($0) > 0) {
                    throw new TestRuntimeException("");
                }
            }
        });

        BigInteger initValue = $0;
        BigIntegerLimiter limiter = setting.apply(limitVerifierHandler, initValue);

        BigInteger minusValue = $1;
        limiter.minus(minusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue.subtract(minusValue));

        AssertionsEx.assertThatThrownBy(() -> limiter.minus($0.subtract($2)))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue.subtract(minusValue));
    }

    public void test_increment(BiFunction<BigIntegerLimitVerifierHandler, BigInteger, BigIntegerLimiter> setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                AssertionsEx.assertThat(plus).isEqualTo($1);
                checkPoint.set(true);
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        BigInteger initValue = $0;
        BigIntegerLimiter limiter = setting.apply(limitVerifierHandler, initValue);
        limiter.increment();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

    public void test_decrement(BiFunction<BigIntegerLimitVerifierHandler, BigInteger, BigIntegerLimiter> setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        BigIntegerLimitVerifierHandler limitVerifierHandler = new BigIntegerLimitVerifierHandler(new BigIntegerLimitVerifier() {
            @Override
            public void verifySet(BigInteger target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigInteger target, BigInteger plus, BigInteger result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(BigInteger target, BigInteger minus, BigInteger result) throws RuntimeException {
                AssertionsEx.assertThat(minus).isEqualTo($1);
                checkPoint.set(true);
            }
        });

        BigInteger initValue = $0;
        BigIntegerLimiter limiter = setting.apply(limitVerifierHandler, initValue);
        limiter.decrement();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

}
