package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class IntAtomicCycleTest extends IntAbstractCycleTest {

    @Test
    public void range() {
        super.range(IntCycleBuilder::withAtomic);
    }

    @Test
    public void start() {
        super.start(IntCycleBuilder::withAtomic);
    }

    @Test
    public void next() {
        super.next(IntCycleBuilder::withAtomic);
    }

}
