package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class BigIntegerAtomicLimiterTest extends BigIntegerAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void set() {
        test_set(BigIntegerBaseLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(BigIntegerBaseLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(BigIntegerBaseLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(BigIntegerBaseLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(BigIntegerBaseLimiter::new);
    }

}
