package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class BigDecimalAtomicLimiterTest extends BigDecimalAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void min_max() {
        test_min_max(BigDecimalAtomicLimiter::new);
    }

    @Test
    public void set() {
        test_set(BigDecimalAtomicLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(BigDecimalAtomicLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(BigDecimalAtomicLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(BigDecimalAtomicLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(BigDecimalAtomicLimiter::new);
    }

}
