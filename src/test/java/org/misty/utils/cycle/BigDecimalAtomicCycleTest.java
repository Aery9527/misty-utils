package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class BigDecimalAtomicCycleTest extends BigDecimalAbstractCycleTest {

    @Test
    public void range() {
        super.range(BigDecimalCycleBuilder::withAtomic);
    }

    @Test
    public void start() {
        super.start(BigDecimalCycleBuilder::withAtomic);
    }

    @Test
    public void next() {
        super.next(BigDecimalCycleBuilder::withAtomic);
    }

}
