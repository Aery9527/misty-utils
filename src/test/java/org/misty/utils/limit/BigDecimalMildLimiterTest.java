package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class BigDecimalMildLimiterTest {

    private final BigDecimal $0 = BigDecimal.ZERO;

    private final BigDecimal $1 = BigDecimal.valueOf(1);

    private final BigDecimal $2 = BigDecimal.valueOf(2);

    private final BigDecimal $3 = BigDecimal.valueOf(3);

    private final BigDecimal $4 = BigDecimal.valueOf(4);

    @Test
    public void set() {
        Consumer<Consumer<BigDecimalLimiterBuilder>> test = setting -> {
            BigDecimalLimiterBuilder limiterBuilder = Limiter.bigDecimalBuilder("kerker")
                    .giveMinLimit($1, true)
                    .giveMaxLimit($3, true);
            setting.accept(limiterBuilder);
            BigDecimalMildLimiter mildLimiter = limiterBuilder.buildMildLimiter($2);

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
        Consumer<Consumer<BigDecimalLimiterBuilder>> test = setting -> {
            BigDecimalLimiterBuilder limiterBuilder = Limiter.bigDecimalBuilder("kerker")
                    .giveMinLimit($1, true)
                    .giveMaxLimit($3, true);
            setting.accept(limiterBuilder);
            BigDecimalMildLimiter mildLimiter = limiterBuilder.buildMildLimiter($2);

            AssertionsEx.assertThat(mildLimiter.plus($1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get().toString()).isEqualTo("3.00");

            AssertionsEx.assertThat(mildLimiter.plus($1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get().toString()).isEqualTo("3.00");

            AssertionsEx.assertThat(mildLimiter.plus($0.subtract($3))).isFalse();
            AssertionsEx.assertThat(mildLimiter.get().toString()).isEqualTo("3.00");
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void minus() {
        Consumer<Consumer<BigDecimalLimiterBuilder>> test = setting -> {
            BigDecimalLimiterBuilder limiterBuilder = Limiter.bigDecimalBuilder("kerker")
                    .giveMinLimit($1, true)
                    .giveMaxLimit($3, true);
            setting.accept(limiterBuilder);
            BigDecimalMildLimiter mildLimiter = limiterBuilder.buildMildLimiter($2);

            AssertionsEx.assertThat(mildLimiter.minus($1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get().toString()).isEqualTo("1.00");

            AssertionsEx.assertThat(mildLimiter.minus($1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get().toString()).isEqualTo("1.00");

            AssertionsEx.assertThat(mildLimiter.minus($0.subtract($3))).isFalse();
            AssertionsEx.assertThat(mildLimiter.get().toString()).isEqualTo("1.00");
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

}
