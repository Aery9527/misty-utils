package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class LongAtomicLimiterTest extends LongAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void set() {
        test_set(LongAtomicLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(LongAtomicLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(LongAtomicLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(LongAtomicLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(LongAtomicLimiter::new);
    }

}
