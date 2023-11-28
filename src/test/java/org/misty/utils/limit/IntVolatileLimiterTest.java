package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class IntVolatileLimiterTest extends IntAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withVolatile);
    }

    @Test
    public void set() {
        test_set(IntVolatileLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(IntVolatileLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(IntVolatileLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(IntVolatileLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(IntVolatileLimiter::new);
    }

}
