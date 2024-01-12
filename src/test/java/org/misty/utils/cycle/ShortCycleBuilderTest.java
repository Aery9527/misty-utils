package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class ShortCycleBuilderTest {

    @Test
    public void build() {
        AssertionsEx.assertThat(Cycle.shortCycleBuilder().giveRange((short) 0, (short) 10).withBase().build()).isInstanceOf(ShortBaseCycle.class);
        AssertionsEx.assertThat(Cycle.shortCycleBuilder().giveRange((short) 0, (short) 10).withVolatile().build()).isInstanceOf(ShortVolatileCycle.class);
        AssertionsEx.assertThat(Cycle.shortCycleBuilder().giveRange((short) 0, (short) 10).withAtomic().build()).isInstanceOf(ShortAtomicCycle.class);

        AssertionsEx.awareThrown(() -> Cycle.shortCycleBuilder().build()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void giveRange() {
        AssertionsEx.awareThrown(() -> Cycle.shortCycleBuilder().giveRange((short) 0, (short) 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.shortCycleBuilder().giveRange((short) 0, (short) -1)).isInstanceOf(IllegalArgumentException.class);

        ShortCycleBuilder builder = Cycle.shortCycleBuilder().giveRange((short) 5566, (short) 9527);
        AssertionsEx.assertThat(builder.getMin()).isEqualTo((short) 5566);
        AssertionsEx.assertThat(builder.getMax()).isEqualTo((short) 9527);
        AssertionsEx.assertThat(builder.getInitValue()).isEqualTo((short) 5566); // default init value is min
    }

    @Test
    public void giveInitValue() {
        AssertionsEx.awareThrown(() -> Cycle.shortCycleBuilder().giveInitValue((short) 2266)).isInstanceOf(IllegalStateException.class);
        AssertionsEx.awareThrown(() -> Cycle.shortCycleBuilder().giveRange((short) 0, (short) 1).giveInitValue((short) -1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.shortCycleBuilder().giveRange((short) 0, (short) 1).giveInitValue((short) 2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveStep() {
        Cycle.shortCycleBuilder().giveStep((short) -2);
        Cycle.shortCycleBuilder().giveStep((short) -1);
        Cycle.shortCycleBuilder().giveStep((short) 1);
        Cycle.shortCycleBuilder().giveStep((short) 2);

        AssertionsEx.awareThrown(() -> Cycle.shortCycleBuilder().giveStep((short) 0)).isInstanceOf(IllegalArgumentException.class);
    }

}
