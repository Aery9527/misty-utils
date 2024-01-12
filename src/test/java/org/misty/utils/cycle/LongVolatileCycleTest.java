package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class LongVolatileCycleTest extends ShortAbstractCycleTest {

    @Test
    public void range() {
        super.range(ShortCycleBuilder::withVolatile);
    }

    @Test
    public void start() {
        super.start(ShortCycleBuilder::withVolatile);
    }

    @Test
    public void next() {
        super.next(ShortCycleBuilder::withVolatile);
    }

}
