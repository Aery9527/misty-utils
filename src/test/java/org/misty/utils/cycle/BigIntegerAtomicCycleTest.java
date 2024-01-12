package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class BigIntegerAtomicCycleTest extends BigIntegerAbstractCycleTest {

    @Test
    public void range() {
        super.range(BigIntegerCycleBuilder::withAtomic);
    }

    @Test
    public void start() {
        super.start(BigIntegerCycleBuilder::withAtomic);
    }

    @Test
    public void next() {
        super.next(BigIntegerCycleBuilder::withAtomic);
    }

}
