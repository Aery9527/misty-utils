package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class ShortVolatileLimiterTest extends ShortAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withVolatile);
    }

    @Test
    public void min_max() {
        test_min_max(ShortVolatileLimiter::new);
    }

    @Test
    public void set() {
        test_set(ShortVolatileLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(ShortVolatileLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(ShortVolatileLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(ShortVolatileLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(ShortVolatileLimiter::new);
    }

}
