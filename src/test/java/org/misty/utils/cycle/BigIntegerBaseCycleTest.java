package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;

public class BigIntegerBaseCycleTest extends BigIntegerAbstractCycleTest {

    @Test
    public void range() {
        super.range(BigIntegerCycleBuilder::withBase);
    }

    @Test
    public void start() {
        super.start(BigIntegerCycleBuilder::withBase);
    }

    @Test
    public void next() {
        super.next(BigIntegerCycleBuilder::withBase);
    }

}
