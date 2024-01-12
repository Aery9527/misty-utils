package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;

public class BigDecimalCycleBuilderTest {

    private final BigDecimal $_2 = BigDecimal.valueOf(-2);

    private final BigDecimal $_1 = BigDecimal.valueOf(-1);

    private final BigDecimal $0 = BigDecimal.valueOf(0);

    private final BigDecimal $1 = BigDecimal.valueOf(1);

    private final BigDecimal $2 = BigDecimal.valueOf(2);

    private final BigDecimal $10 = BigDecimal.valueOf(10);

    @Test
    public void build() {
        AssertionsEx.assertThat(Cycle.bigDecimalCycleBuilder().giveRange($0, $10).withBase().build()).isInstanceOf(BigDecimalBaseCycle.class);
        AssertionsEx.assertThat(Cycle.bigDecimalCycleBuilder().giveRange($0, $10).withVolatile().build()).isInstanceOf(BigDecimalVolatileCycle.class);
        AssertionsEx.assertThat(Cycle.bigDecimalCycleBuilder().giveRange($0, $10).withAtomic().build()).isInstanceOf(BigDecimalAtomicCycle.class);

        AssertionsEx.awareThrown(() -> Cycle.bigDecimalCycleBuilder().build()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void giveRange() {
        AssertionsEx.awareThrown(() -> Cycle.bigDecimalCycleBuilder().giveRange($0, $0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.bigDecimalCycleBuilder().giveRange($0, $_1)).isInstanceOf(IllegalArgumentException.class);

        BigDecimalCycleBuilder builder = Cycle.bigDecimalCycleBuilder().giveRange(BigDecimal.valueOf(5566), BigDecimal.valueOf(9527));
        AssertionsEx.assertThat(builder.getMin()).isEqualTo(BigDecimal.valueOf(5566).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE));
        AssertionsEx.assertThat(builder.getMax()).isEqualTo(BigDecimal.valueOf(9527).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE));
        AssertionsEx.assertThat(builder.getInitValue()).isEqualTo(BigDecimal.valueOf(5566).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE)); // default init value is min
    }

    @Test
    public void giveInitValue() {
        AssertionsEx.awareThrown(() -> Cycle.bigDecimalCycleBuilder().giveInitValue(BigDecimal.valueOf(2266))).isInstanceOf(IllegalStateException.class);
        AssertionsEx.awareThrown(() -> Cycle.bigDecimalCycleBuilder().giveRange($0, $1).giveInitValue($_1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.bigDecimalCycleBuilder().giveRange($0, $1).giveInitValue($2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveStep() {
        Cycle.bigDecimalCycleBuilder().giveStep($_2);
        Cycle.bigDecimalCycleBuilder().giveStep($_1);
        Cycle.bigDecimalCycleBuilder().giveStep($1);
        Cycle.bigDecimalCycleBuilder().giveStep($2);

        AssertionsEx.awareThrown(() -> Cycle.bigDecimalCycleBuilder().giveStep($0)).isInstanceOf(IllegalArgumentException.class);
    }

}
