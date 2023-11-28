package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.Consumer;

public class DoubleMildLimiterTest {

    @Test
    public void set() {
        Consumer<Consumer<DoubleLimiterBuilder>> test = setting -> {
            DoubleLimiterBuilder limiterBuilder = Limiter.doubleLimiterBuilder("kerker")
                    .giveMinLimit(1d, true)
                    .giveMaxLimit(3d, true);
            setting.accept(limiterBuilder);
            DoubleMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2d);

            AssertionsEx.assertThat(mildLimiter.set(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1d);

            AssertionsEx.assertThat(mildLimiter.set(0)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1d);

            AssertionsEx.assertThat(mildLimiter.set(4)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1d);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void plus() {
        Consumer<Consumer<DoubleLimiterBuilder>> test = setting -> {
            DoubleLimiterBuilder limiterBuilder = Limiter.doubleLimiterBuilder("kerker")
                    .giveMinLimit(1d, true)
                    .giveMaxLimit(3d, true);
            setting.accept(limiterBuilder);
            DoubleMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2d);

            AssertionsEx.assertThat(mildLimiter.plus(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3d);

            AssertionsEx.assertThat(mildLimiter.plus(1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3d);

            AssertionsEx.assertThat(mildLimiter.plus(-3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3d);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void minus() {
        Consumer<Consumer<DoubleLimiterBuilder>> test = setting -> {
            DoubleLimiterBuilder limiterBuilder = Limiter.doubleLimiterBuilder("kerker")
                    .giveMinLimit(1d, true)
                    .giveMaxLimit(3d, true);
            setting.accept(limiterBuilder);
            DoubleMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2d);

            AssertionsEx.assertThat(mildLimiter.minus(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1d);

            AssertionsEx.assertThat(mildLimiter.minus(1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1d);

            AssertionsEx.assertThat(mildLimiter.minus(-3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1d);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

}
