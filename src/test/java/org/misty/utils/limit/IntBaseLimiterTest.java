package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class IntBaseLimiterTest extends IntAbstractLimiterTest {

    @Test
    public void build() {
        test_build(AbstractLimiterBuilder::withBase);
    }

    @Test
    public void min_max() {
        test_min_max(IntBaseLimiter::new);
    }

    @Test
    public void set() {
        test_set(IntBaseLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(IntBaseLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(IntBaseLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(IntBaseLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(IntBaseLimiter::new);
    }

}
