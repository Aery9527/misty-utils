package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class LongVolatileLimiterTest extends LongAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withVolatile);
    }

    @Test
    public void set() {
        test_set(LongBaseLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(LongBaseLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(LongBaseLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(LongBaseLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(LongBaseLimiter::new);
    }

}
