package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class FloatAtomicLimiterTest extends FloatAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void set() {
        test_set(FloatAtomicLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(FloatAtomicLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(FloatAtomicLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(FloatAtomicLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(FloatAtomicLimiter::new);
    }

}
