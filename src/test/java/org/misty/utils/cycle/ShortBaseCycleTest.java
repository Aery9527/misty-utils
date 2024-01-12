package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class ShortBaseCycleTest extends ShortAbstractCycleTest {

    @Test
    public void range() {
        super.range(ShortCycleBuilder::withBase);
    }

    @Test
    public void start() {
        super.start(ShortCycleBuilder::withBase);
    }

    @Test
    public void next() {
        super.next(ShortCycleBuilder::withBase);
    }

}
