package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class LongCycleBuilderTest {

    @Test
    public void build() {
        AssertionsEx.assertThat(Cycle.longCycleBuilder().giveRange(0, 10).withBase().build()).isInstanceOf(LongBaseCycle.class);
        AssertionsEx.assertThat(Cycle.longCycleBuilder().giveRange(0, 10).withVolatile().build()).isInstanceOf(LongVolatileCycle.class);
        AssertionsEx.assertThat(Cycle.longCycleBuilder().giveRange(0, 10).withAtomic().build()).isInstanceOf(LongAtomicCycle.class);

        AssertionsEx.awareThrown(() -> Cycle.longCycleBuilder().build()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void giveRange() {
        AssertionsEx.awareThrown(() -> Cycle.longCycleBuilder().giveRange(0, 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.longCycleBuilder().giveRange(0, -1)).isInstanceOf(IllegalArgumentException.class);

        LongCycleBuilder builder = Cycle.longCycleBuilder().giveRange(5566, 9527);
        AssertionsEx.assertThat(builder.getMin()).isEqualTo(5566);
        AssertionsEx.assertThat(builder.getMax()).isEqualTo(9527);
        AssertionsEx.assertThat(builder.getInitValue()).isEqualTo(5566); // default init value is min
    }

    @Test
    public void giveInitValue() {
        AssertionsEx.awareThrown(() -> Cycle.longCycleBuilder().giveInitValue(2266)).isInstanceOf(IllegalStateException.class);
        AssertionsEx.awareThrown(() -> Cycle.longCycleBuilder().giveRange(0, 1).giveInitValue(-1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.longCycleBuilder().giveRange(0, 1).giveInitValue(2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveStep() {
        Cycle.longCycleBuilder().giveStep(-2);
        Cycle.longCycleBuilder().giveStep(-1);
        Cycle.longCycleBuilder().giveStep(1);
        Cycle.longCycleBuilder().giveStep(2);

        AssertionsEx.awareThrown(() -> Cycle.longCycleBuilder().giveStep(0)).isInstanceOf(IllegalArgumentException.class);
    }

}
