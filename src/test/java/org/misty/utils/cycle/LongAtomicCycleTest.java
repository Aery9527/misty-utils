package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class LongAtomicCycleTest extends LongAbstractCycleTest {

    @Test
    public void range() {
        super.range(LongCycleBuilder::withAtomic);
    }

    @Test
    public void start() {
        super.start(LongCycleBuilder::withAtomic);
    }

    @Test
    public void next() {
        super.next(LongCycleBuilder::withAtomic);
    }

}
