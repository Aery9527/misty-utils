package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class ShortAtomicCycleTest extends ShortAbstractCycleTest {

    @Test
    public void range() {
        super.range(ShortCycleBuilder::withAtomic);
    }

    @Test
    public void start() {
        super.start(ShortCycleBuilder::withAtomic);
    }

    @Test
    public void next() {
        super.next(ShortCycleBuilder::withAtomic);
    }

}
