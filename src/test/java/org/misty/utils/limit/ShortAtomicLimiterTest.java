package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class ShortAtomicLimiterTest extends ShortAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withAtomic);
    }

    @Test
    public void set() {
        test_set(ShortAtomicLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(ShortAtomicLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(ShortAtomicLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(ShortAtomicLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(ShortAtomicLimiter::new);
    }

}
