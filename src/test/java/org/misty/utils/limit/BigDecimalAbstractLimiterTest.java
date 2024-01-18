package org.misty.utils.limit;

import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.Verifier;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class BigDecimalAbstractLimiterTest {

    public interface TestInterface {
        BigDecimalLimiter setting(BigDecimalLimitVerifierHandler verifierHandler, BigDecimal min, BigDecimal max, BigDecimal initValue);
    }

    private final BigDecimal $0 = BigDecimal.ZERO;

    private final BigDecimal $1 = BigDecimal.valueOf(1);

    private final BigDecimal $2 = BigDecimal.valueOf(2);

    private final BigDecimal $3 = BigDecimal.valueOf(3);

    private final BigDecimalLimitVerifierHandler mockVerifierHandler = Mockito.mock(BigDecimalLimitVerifierHandler.class);

    private final BigDecimal min = BigDecimal.valueOf(2266);

    private final BigDecimal max = BigDecimal.valueOf(9527);

    public void teset_build(Consumer<BigDecimalLimiterBuilder> setting) {
        BigDecimal min = $1;
        BigDecimal max = $3;
        BigDecimal initValue = $2;

        String term = "kerker";
        BigDecimalLimiterBuilder limiterBuilder = Limiter.bigDecimalBuilder(term)
                .giveMinLimit(min, true)
                .giveMaxLimit(max, true);
        setting.accept(limiterBuilder);

        BigDecimalLimiter limiter = limiterBuilder.build(initValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue.setScale(BigDecimalLimiter.DEFAULT_SCALE, BigDecimalLimiter.DEFAULT_ROUNDING_MODE));

        AssertionsEx.awareThrown(() -> limiterBuilder.build($0))
                .hasMessage(term + " " + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, Limiter.ErrorMsgFormat.SET_TERM, "0.00", min, max))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void test_min_max(TestInterface setting) {
        BigDecimalLimiter limiter = setting.setting(this.mockVerifierHandler, this.min, this.max, BigDecimal.valueOf(5566));
        AssertionsEx.assertThat(limiter.getMin().get()).isEqualTo(this.min);
        AssertionsEx.assertThat(limiter.getMax().get()).isEqualTo(this.max);
    }

    public void test_set(TestInterface setting) {
        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(scale, roundingMode, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
                if (target.compareTo($0) == 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        BigDecimal initValue = $2;
        BigDecimalLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        BigDecimal setValue = BigDecimal.valueOf(Math.random() * 100);
        limiter.set(setValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue.setScale(scale, roundingMode));

        AssertionsEx.assertThatThrownBy(() -> limiter.set($0))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue.setScale(scale, roundingMode));
    }

    public void test_plus(TestInterface setting) {
        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(2, RoundingMode.HALF_UP, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                if (result.compareTo($0) < 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        BigDecimal initValue = $0;
        BigDecimalLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        BigDecimal plusValue = $1;
        BigDecimal result = limiter.plus(plusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(limitVerifierHandler.setScale(initValue.add(plusValue)));
        AssertionsEx.assertThat(result).isEqualTo(limitVerifierHandler.setScale(initValue.add(plusValue)));

        AssertionsEx.assertThatThrownBy(() -> limiter.plus($0.subtract($2)))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(limitVerifierHandler.setScale(initValue.add(plusValue)));
    }

    public void test_minus(TestInterface setting) {
        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(2, RoundingMode.HALF_UP, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                if (result.compareTo($0) > 0) {
                    throw new TestRuntimeException("");
                }
            }
        });

        BigDecimal initValue = $0;
        BigDecimalLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        BigDecimal minusValue = $1;
        limiter.minus(minusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(limitVerifierHandler.setScale(initValue.subtract(minusValue)));

        AssertionsEx.assertThatThrownBy(() -> limiter.minus($0.subtract($2)))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(limitVerifierHandler.setScale(initValue.subtract(minusValue)));
    }

    public void test_increment(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(2, RoundingMode.HALF_UP, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                AssertionsEx.assertThat(plus).isEqualTo($1);
                checkPoint.set(true);
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        BigDecimal initValue = $0;
        BigDecimalLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.increment();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

    public void test_decrement(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        BigDecimalLimitVerifierHandler limitVerifierHandler = new BigDecimalLimitVerifierHandler(2, RoundingMode.HALF_UP, new BigDecimalLimitVerifier() {
            @Override
            public void verifySet(BigDecimal target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(BigDecimal target, BigDecimal plus, BigDecimal result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(BigDecimal target, BigDecimal minus, BigDecimal result) throws RuntimeException {
                AssertionsEx.assertThat(minus).isEqualTo($1);
                checkPoint.set(true);
            }
        });

        BigDecimal initValue = $0;
        BigDecimalLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.decrement();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

}
