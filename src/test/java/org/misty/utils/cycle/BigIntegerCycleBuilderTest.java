package org.misty.utils.cycle;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigInteger;

class BigIntegerCycleBuilderTest {

    private final BigInteger $_2 = BigInteger.valueOf(-2);

    private final BigInteger $_1 = BigInteger.valueOf(-1);

    private final BigInteger $0 = BigInteger.valueOf(0);

    private final BigInteger $1 = BigInteger.valueOf(1);

    private final BigInteger $2 = BigInteger.valueOf(2);

    private final BigInteger $10 = BigInteger.valueOf(10);

    @Test
    public void build() {
        AssertionsEx.assertThat(Cycle.bigIntegerCycleBuilder().giveRange($0, $10).withBase().build()).isInstanceOf(BigIntegerBaseCycle.class);
        AssertionsEx.assertThat(Cycle.bigIntegerCycleBuilder().giveRange($0, $10).withVolatile().build()).isInstanceOf(BigIntegerVolatileCycle.class);
        AssertionsEx.assertThat(Cycle.bigIntegerCycleBuilder().giveRange($0, $10).withAtomic().build()).isInstanceOf(BigIntegerAtomicCycle.class);

        AssertionsEx.awareThrown(() -> Cycle.bigIntegerCycleBuilder().build()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void giveRange() {
        AssertionsEx.awareThrown(() -> Cycle.bigIntegerCycleBuilder().giveRange($0, $0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.bigIntegerCycleBuilder().giveRange($0, $_1)).isInstanceOf(IllegalArgumentException.class);

        BigIntegerCycleBuilder builder = Cycle.bigIntegerCycleBuilder().giveRange(BigInteger.valueOf(5566), BigInteger.valueOf(9527));
        AssertionsEx.assertThat(builder.getMin()).isEqualTo(BigInteger.valueOf(5566));
        AssertionsEx.assertThat(builder.getMax()).isEqualTo(BigInteger.valueOf(9527));
        AssertionsEx.assertThat(builder.getInitValue()).isEqualTo(BigInteger.valueOf(5566)); // default init value is min
    }

    @Test
    public void giveInitValue() {
        AssertionsEx.awareThrown(() -> Cycle.bigIntegerCycleBuilder().giveInitValue(BigInteger.valueOf(2266))).isInstanceOf(IllegalStateException.class);
        AssertionsEx.awareThrown(() -> Cycle.bigIntegerCycleBuilder().giveRange($0, $1).giveInitValue($_1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Cycle.bigIntegerCycleBuilder().giveRange($0, $1).giveInitValue($2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void giveStep() {
        Cycle.bigIntegerCycleBuilder().giveStep($_2);
        Cycle.bigIntegerCycleBuilder().giveStep($_1);
        Cycle.bigIntegerCycleBuilder().giveStep($1);
        Cycle.bigIntegerCycleBuilder().giveStep($2);

        AssertionsEx.awareThrown(() -> Cycle.bigIntegerCycleBuilder().giveStep($0)).isInstanceOf(IllegalArgumentException.class);
    }

}
