package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class IntVolatileCycleTest extends IntAbstractCycleTest {

    @Test
    public void range() {
        super.range(IntCycleBuilder::withVolatile);
    }

    @Test
    public void start() {
        super.start(IntCycleBuilder::withVolatile);
    }

    @Test
    public void next() {
        super.next(IntCycleBuilder::withVolatile);
    }

}
