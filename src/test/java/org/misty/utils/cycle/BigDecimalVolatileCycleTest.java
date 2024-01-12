package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class BigDecimalVolatileCycleTest extends BigDecimalAbstractCycleTest {

    @Test
    public void range() {
        super.range(BigDecimalCycleBuilder::withVolatile);
    }

    @Test
    public void start() {
        super.start(BigDecimalCycleBuilder::withVolatile);
    }

    @Test
    public void next() {
        super.next(BigDecimalCycleBuilder::withVolatile);
    }

}
