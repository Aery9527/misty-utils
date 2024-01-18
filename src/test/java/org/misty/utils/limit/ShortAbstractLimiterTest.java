package org.misty.utils.limit;

import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.Verifier;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ShortAbstractLimiterTest {

    public interface TestInterface {
        ShortLimiter setting(ShortLimitVerifierHandler verifierHandler, short min, short max, short initValue);
    }

    private final ShortLimitVerifierHandler mockVerifierHandler = Mockito.mock(ShortLimitVerifierHandler.class);

    private final short min = 2266;

    private final short max = 9527;

    public void teset_build(Consumer<ShortLimiterBuilder> setting) {
        short min = 1;
        short max = 3;
        short initValue = 2;

        String term = "kerker";
        ShortLimiterBuilder limiterBuilder = Limiter.shortLimiterBuilder(term)
                .giveMinLimit(min, true)
                .giveMaxLimit(max, true);
        setting.accept(limiterBuilder);

        ShortLimiter limiter = limiterBuilder.build(initValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(initValue);

        AssertionsEx.awareThrown(() -> limiterBuilder.build((short) 0))
                .hasMessage(term + " " + String.format(Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE, Limiter.ErrorMsgFormat.SET_TERM, 0, min, max))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void test_min_max(TestInterface setting) {
        ShortLimiter limiter = setting.setting(this.mockVerifierHandler, this.min, this.max, (short) 5566);
        AssertionsEx.assertThat(limiter.getMin().get()).isEqualTo(this.min);
        AssertionsEx.assertThat(limiter.getMax().get()).isEqualTo(this.max);
    }

    public void test_set(TestInterface setting) {
        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler("kerker", null, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
                if (target == 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        short initValue = 2;
        ShortLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        short setValue = 1;
        limiter.set(setValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);

        AssertionsEx.assertThatThrownBy(() -> limiter.set((short) 0))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo(setValue);
    }

    public void test_plus(TestInterface setting) {
        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler("kerker", null, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                if (result < 0) {
                    throw new TestRuntimeException("");
                }
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        short initValue = 0;
        ShortLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        short plusValue = 1;
        short result = limiter.plus(plusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo((short) (initValue + plusValue));
        AssertionsEx.assertThat(result).isEqualTo((short) (initValue + plusValue));

        AssertionsEx.assertThatThrownBy(() -> limiter.plus((short) -2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo((short) (initValue + plusValue));
    }

    public void test_minus(TestInterface setting) {
        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler("kerker", null, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                if (result > 0) {
                    throw new TestRuntimeException("");
                }
            }
        });

        short initValue = 0;
        ShortLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);

        short minusValue = 1;
        limiter.minus(minusValue);
        AssertionsEx.assertThat(limiter.get()).isEqualTo((short) (initValue - minusValue));

        AssertionsEx.assertThatThrownBy(() -> limiter.minus((short) -2))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThat(limiter.get()).isEqualTo((short) (initValue - minusValue));
    }

    public void test_increment(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler("kerker", null, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                AssertionsEx.assertThat(plus).isEqualTo((short) 1);
                checkPoint.set(true);
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }
        });

        short initValue = 0;
        ShortLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.increment();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

    public void test_decrement(TestInterface setting) {
        AtomicBoolean checkPoint = new AtomicBoolean(false);

        ShortLimitVerifierHandler limitVerifierHandler = new ShortLimitVerifierHandler("kerker", null, new ShortLimitVerifier() {
            @Override
            public void verifySet(short target) throws RuntimeException {
            }

            @Override
            public void verifyPlus(short target, short plus, short result) throws RuntimeException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void verifyMinus(short target, short minus, short result) throws RuntimeException {
                AssertionsEx.assertThat(minus).isEqualTo((short) 1);
                checkPoint.set(true);
            }
        });

        short initValue = 0;
        ShortLimiter limiter = setting.setting(limitVerifierHandler, this.min, this.max, initValue);
        limiter.decrement();
        AssertionsEx.assertThat(checkPoint.get()).isTrue();
    }

}
