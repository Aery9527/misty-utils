package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.function.Consumer;

public class IntMildLimiterTest {

    @Test
    public void set() {
        Consumer<Consumer<IntLimiterBuilder>> test = setting -> {
            IntLimiterBuilder limiterBuilder = Limiter.intLimiterBuilder("kerker")
                    .giveMinLimit(1, true)
                    .giveMaxLimit(3, true);
            setting.accept(limiterBuilder);
            IntMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2);

            AssertionsEx.assertThat(mildLimiter.set(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1);

            AssertionsEx.assertThat(mildLimiter.set(0)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1);

            AssertionsEx.assertThat(mildLimiter.set(4)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void plus() {
        Consumer<Consumer<IntLimiterBuilder>> test = setting -> {
            IntLimiterBuilder limiterBuilder = Limiter.intLimiterBuilder("kerker")
                    .giveMinLimit(1, true)
                    .giveMaxLimit(3, true);
            setting.accept(limiterBuilder);
            IntMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2);

            AssertionsEx.assertThat(mildLimiter.plus(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3);

            AssertionsEx.assertThat(mildLimiter.plus(1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3);

            AssertionsEx.assertThat(mildLimiter.plus(-3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(3);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void minus() {
        Consumer<Consumer<IntLimiterBuilder>> test = setting -> {
            IntLimiterBuilder limiterBuilder = Limiter.intLimiterBuilder("kerker")
                    .giveMinLimit(1, true)
                    .giveMaxLimit(3, true);
            setting.accept(limiterBuilder);
            IntMildLimiter mildLimiter = limiterBuilder.buildMildLimiter(2);

            AssertionsEx.assertThat(mildLimiter.minus(1)).isTrue();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1);

            AssertionsEx.assertThat(mildLimiter.minus(1)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1);

            AssertionsEx.assertThat(mildLimiter.minus(-3)).isFalse();
            AssertionsEx.assertThat(mildLimiter.get()).isEqualTo(1);
        };

        test.accept(AbstractLimiterBuilder::withBase);
        test.accept(AbstractLimiterBuilder::withVolatile);
        test.accept(AbstractLimiterBuilder::withAtomic);
    }

}
