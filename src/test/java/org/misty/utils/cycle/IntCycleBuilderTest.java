package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class IntCycleBuilderTest {

    @Test
    public void build() {
        AssertionsEx.assertThat(Cycle.intCycleBuilder().giveRange(0, 10).withBase().build()).isInstanceOf(IntBaseCycle.class);
        AssertionsEx.assertThat(Cycle.intCycleBuilder().giveRange(0, 10).withVolatile().build()).isInstanceOf(IntVolatileCycle.class);
        AssertionsEx.assertThat(Cycle.intCycleBuilder().giveRange(0, 10).withAtomic().build()).isInstanceOf(IntAtomicCycle.class);

        AssertionsEx.awareThrown(() -> Cycle.intCycleBuilder().build()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void giveRange() {
        AssertionsEx.awareThrown(() -> Cycle.intCycleBuilder().giveRange(0, 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.intCycleBuilder().giveRange(0, -1)).isInstanceOf(IllegalArgumentException.class);

        IntCycleBuilder builder = Cycle.intCycleBuilder().giveRange(5566, 9527);
        AssertionsEx.assertThat(builder.getMin()).isEqualTo(5566);
        AssertionsEx.assertThat(builder.getMax()).isEqualTo(9527);
        AssertionsEx.assertThat(builder.getInitValue()).isEqualTo(5566); // default init value is min
    }

    @Test
    public void giveInitValue() {
        AssertionsEx.awareThrown(() -> Cycle.intCycleBuilder().giveInitValue(2266)).isInstanceOf(IllegalStateException.class);
        AssertionsEx.awareThrown(() -> Cycle.intCycleBuilder().giveRange(0, 1).giveInitValue(-1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.intCycleBuilder().giveRange(0, 1).giveInitValue(2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveStep() {
        Cycle.intCycleBuilder().giveStep(-2);
        Cycle.intCycleBuilder().giveStep(-1);
        Cycle.intCycleBuilder().giveStep(1);
        Cycle.intCycleBuilder().giveStep(2);

        AssertionsEx.awareThrown(() -> Cycle.intCycleBuilder().giveStep(0)).isInstanceOf(IllegalArgumentException.class);
    }

}
