package org.misty.utils.limit;

import org.junit.jupiter.api.Test;

public class DoubleBaseLimiterTest extends DoubleAbstractLimiterTest {

    @Test
    public void build() {
        teset_build(AbstractLimiterBuilder::withBase);
    }

    @Test
    public void min_max() {
        test_min_max(DoubleBaseLimiter::new);
    }

    @Test
    public void set() {
        test_set(DoubleBaseLimiter::new);
    }

    @Test
    public void plus() {
        test_plus(DoubleBaseLimiter::new);
    }

    @Test
    public void minus() {
        test_minus(DoubleBaseLimiter::new);
    }

    @Test
    public void increment() {
        test_increment(DoubleBaseLimiter::new);
    }

    @Test
    public void decrement() {
        test_decrement(DoubleBaseLimiter::new);
    }

}
