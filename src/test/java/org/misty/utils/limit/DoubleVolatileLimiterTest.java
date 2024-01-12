package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class DoubleVolatileLimiterTest extends DoubleAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withVolatile);
    }

    @Test
    public void min_max() {
        test_min_max(DoubleVolatileLimiter::new);
    }

    @Test
    public void set() {
        test_set(DoubleVolatileLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(DoubleVolatileLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(DoubleVolatileLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(DoubleVolatileLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(DoubleVolatileLimiter::new);
    }

}
