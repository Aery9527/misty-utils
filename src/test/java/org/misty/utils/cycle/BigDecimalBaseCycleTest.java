package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class BigDecimalBaseCycleTest extends BigDecimalAbstractCycleTest {

    @Test
    public void range() {
        super.range(BigDecimalCycleBuilder::withBase);
    }

    @Test
    public void start() {
        super.start(BigDecimalCycleBuilder::withBase);
    }

    @Test
    public void next() {
        super.next(BigDecimalCycleBuilder::withBase);
    }

}
