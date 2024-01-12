package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class FloatVolatileLimiterTest extends FloatAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withVolatile);
    }

    @Test
    public void min_max() {
        test_min_max(FloatVolatileLimiter::new);
    }

    @Test
    public void set() {
        test_set(FloatVolatileLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(FloatVolatileLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(FloatVolatileLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(FloatVolatileLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(FloatVolatileLimiter::new);
    }

}
