package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class ShortBaseLimiterTest extends ShortAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withBase);
    }

    @Test
    public void set() {
        test_set(ShortBaseLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(ShortBaseLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(ShortBaseLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(ShortBaseLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(ShortBaseLimiter::new);
    }

}
