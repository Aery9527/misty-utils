package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class BigDecimalBaseLimiterTest extends BigDecimalAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withBase);
    }

    @Test
    public void min_max() {
        test_min_max(BigDecimalBaseLimiter::new);
    }

    @Test
    public void set() {
        test_set(BigDecimalBaseLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(BigDecimalBaseLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(BigDecimalBaseLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(BigDecimalBaseLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(BigDecimalBaseLimiter::new);
    }

}
