package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class BigDecimalVolatileLimiterTest extends BigDecimalAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withVolatile);
    }

    @Test
    public void min_max() {
        test_min_max(BigDecimalVolatileLimiter::new);
    }

    @Test
    public void set() {
        test_set(BigDecimalVolatileLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(BigDecimalVolatileLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(BigDecimalVolatileLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(BigDecimalVolatileLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(BigDecimalVolatileLimiter::new);
    }

}
