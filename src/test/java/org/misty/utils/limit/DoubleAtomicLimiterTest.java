package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class DoubleAtomicLimiterTest extends DoubleAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void set() {
        test_set(DoubleAtomicLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(DoubleAtomicLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(DoubleAtomicLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(DoubleAtomicLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(DoubleAtomicLimiter::new);
    }

}
