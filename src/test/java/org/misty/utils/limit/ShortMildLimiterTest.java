package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.Consumer;

public class ShortMildLimiterTest {

    @Test
    public void set() {
        Consumer<Consumer<ShortLimiterBuilder>> test = setting -> {
            ShortLimiterBuilder limiterBuilder = Limiter.shortLimiterBuilder("kerker")
                    .giveMinLimit((short) 1, true)
                    .giveMaxLimit((short) 3, true);
            setting.accept(limiterBuilder);
            ShortMildLimiter mildLimiter = limiterBuilder.buildMildLimiter((short) 2);

            AssertionsEx.assertThat(mildLimiter.set((short) 1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 1);

            AssertionsEx.assertThat(mildLimiter.set((short) 0)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 1);

            AssertionsEx.assertThat(mildLimiter.set((short) 4)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 1);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void plus() {
        Consumer<Consumer<ShortLimiterBuilder>> test = setting -> {
            ShortLimiterBuilder limiterBuilder = Limiter.shortLimiterBuilder("kerker")
                    .giveMinLimit((short) 1, true)
                    .giveMaxLimit((short) 3, true);
            setting.accept(limiterBuilder);
            ShortMildLimiter mildLimiter = limiterBuilder.buildMildLimiter((short) 2);

            AssertionsEx.assertThat(mildLimiter.plus((short) 1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 3);

            AssertionsEx.assertThat(mildLimiter.plus((short) 1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 3);

            AssertionsEx.assertThat(mildLimiter.plus((short) -3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 3);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void minus() {
        Consumer<Consumer<ShortLimiterBuilder>> test = setting -> {
            ShortLimiterBuilder limiterBuilder = Limiter.shortLimiterBuilder("kerker")
                    .giveMinLimit((short) 1, true)
                    .giveMaxLimit((short) 3, true);
            setting.accept(limiterBuilder);
            ShortMildLimiter mildLimiter = limiterBuilder.buildMildLimiter((short) 2);

            AssertionsEx.assertThat(mildLimiter.minus((short) 1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 1);

            AssertionsEx.assertThat(mildLimiter.minus((short) 1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 1);

            AssertionsEx.assertThat(mildLimiter.minus((short) -3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo((short) 1);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

}
