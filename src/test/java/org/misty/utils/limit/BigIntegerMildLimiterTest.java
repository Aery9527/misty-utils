package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigInteger;
import java.util.function.Consumer;

public class BigIntegerMildLimiterTest {

    private final BigInteger $0 = BigInteger.ZERO;

    private final BigInteger $1 = BigInteger.valueOf(1);

    private final BigInteger $2 = BigInteger.valueOf(2);

    private final BigInteger $3 = BigInteger.valueOf(3);

    private final BigInteger $4 = BigInteger.valueOf(4);

    @Test
    public void set() {
        Consumer<Consumer<BigIntegerLimiterBuilder>> test = setting -> {
            BigIntegerLimiterBuilder limiterBuilder = Limiter.bigIntegerBuilder("kerker")
                    .giveMinLimit($1, true)
                    .giveMaxLimit($3, true);
            setting.accept(limiterBuilder);
            BigIntegerMildLimiter mildLimiter = limiterBuilder.buildMildLimiter($2);

            AssertionsEx.assertThat(mildLimiter.set($1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($1);

            AssertionsEx.assertThat(mildLimiter.set($0)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($1);

            AssertionsEx.assertThat(mildLimiter.set($4)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($1);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void plus() {
        Consumer<Consumer<BigIntegerLimiterBuilder>> test = setting -> {
            BigIntegerLimiterBuilder limiterBuilder = Limiter.bigIntegerBuilder("kerker")
                    .giveMinLimit($1, true)
                    .giveMaxLimit($3, true);
            setting.accept(limiterBuilder);
            BigIntegerMildLimiter mildLimiter = limiterBuilder.buildMildLimiter($2);

            AssertionsEx.assertThat(mildLimiter.plus($1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($3);

            AssertionsEx.assertThat(mildLimiter.plus($1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($3);

            AssertionsEx.assertThat(mildLimiter.plus($0.subtract($3))).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($3);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void minus() {
        Consumer<Consumer<BigIntegerLimiterBuilder>> test = setting -> {
            BigIntegerLimiterBuilder limiterBuilder = Limiter.bigIntegerBuilder("kerker")
                    .giveMinLimit($1, true)
                    .giveMaxLimit($3, true);
            setting.accept(limiterBuilder);
            BigIntegerMildLimiter mildLimiter = limiterBuilder.buildMildLimiter($2);

            AssertionsEx.assertThat(mildLimiter.minus($1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($1);

            AssertionsEx.assertThat(mildLimiter.minus($1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($1);

            AssertionsEx.assertThat(mildLimiter.minus($0.subtract($3))).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo($1);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

}
