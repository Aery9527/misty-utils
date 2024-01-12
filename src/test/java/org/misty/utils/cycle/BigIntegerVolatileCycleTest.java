package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class BigIntegerVolatileCycleTest extends BigIntegerAbstractCycleTest {

    @Test
    public void range() {
        super.range(BigIntegerCycleBuilder::withVolatile);
    }

    @Test
    public void start() {
        super.start(BigIntegerCycleBuilder::withVolatile);
    }

    @Test
    public void next() {
        super.next(BigIntegerCycleBuilder::withVolatile);
    }

}
