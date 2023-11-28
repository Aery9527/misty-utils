package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.Consumer;

public class FloatMildLimiterTest {

    @Test
    public void set() {
        Consumer<Consumer<FloatLimiterBuilder>> test = setting -> {
            FloatLimiterBuilder limiterBuilder = Limiter.floatLimiterBuilder("kerker")
                    .giveMinLimit(1f, true)
                    .giveMaxLimit(3f, true);
            setting.accept(limiterBuilder);
            FloatMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2f);

            AssertionsEx.assertThat(mildLimiter.set(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1f);

            AssertionsEx.assertThat(mildLimiter.set(0)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1f);

            AssertionsEx.assertThat(mildLimiter.set(4)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1f);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void plus() {
        Consumer<Consumer<FloatLimiterBuilder>> test = setting -> {
            FloatLimiterBuilder limiterBuilder = Limiter.floatLimiterBuilder("kerker")
                    .giveMinLimit(1f, true)
                    .giveMaxLimit(3f, true);
            setting.accept(limiterBuilder);
            FloatMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2f);

            AssertionsEx.assertThat(mildLimiter.plus(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3f);

            AssertionsEx.assertThat(mildLimiter.plus(1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3f);

            AssertionsEx.assertThat(mildLimiter.plus(-3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3f);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void minus() {
        Consumer<Consumer<FloatLimiterBuilder>> test = setting -> {
            FloatLimiterBuilder limiterBuilder = Limiter.floatLimiterBuilder("kerker")
                    .giveMinLimit(1f, true)
                    .giveMaxLimit(3f, true);
            setting.accept(limiterBuilder);
            FloatMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2f);

            AssertionsEx.assertThat(mildLimiter.minus(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1f);

            AssertionsEx.assertThat(mildLimiter.minus(1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1f);

            AssertionsEx.assertThat(mildLimiter.minus(-3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1f);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

}
