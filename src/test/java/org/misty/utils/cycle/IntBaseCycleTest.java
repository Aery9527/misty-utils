package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class IntBaseCycleTest extends IntAbstractCycleTest {

    @Test
    public void range() {
        super.range(IntCycleBuilder::withBase);
    }

    @Test
    public void start() {
        super.start(IntCycleBuilder::withBase);
    }

    @Test
    public void next() {
        super.next(IntCycleBuilder::withBase);
    }

}
