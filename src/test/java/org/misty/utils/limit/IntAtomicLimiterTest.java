package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class IntAtomicLimiterTest extends IntAbstractLimiterTest {

    @Test
    public void build() {
        test_build(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void min_max() {
        test_min_max(IntAtomicLimiter::new);
    }

    @Test
    public void set() {
        test_set(IntAtomicLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(IntAtomicLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(IntAtomicLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(IntAtomicLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(IntAtomicLimiter::new);
    }

}
