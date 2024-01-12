package org.misty.utils.cycle;

import org.misty._utils.AssertionsEx;

import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BigIntegerAbstractCycleTest {

    private final BigInteger $_2 = BigInteger.valueOf(-2);

    private final BigInteger $_1 = BigInteger.valueOf(-1);

    private final BigInteger $0 = BigInteger.valueOf(0);

    private final BigInteger $1 = BigInteger.valueOf(1);

    private final BigInteger $2 = BigInteger.valueOf(2);

    public void range(UnaryOperator<BigIntegerCycleBuilder> setting) {
        BigIntegerCycle cycle = setting.apply(Cycle.bigIntegerCycleBuilder()).giveRange($_2, $2).build();
        AssertionsEx.assertThat(cycle.getMin()).isEqualTo($_2);
        AssertionsEx.assertThat(cycle.getMax()).isEqualTo($2);
    }

    public void start(UnaryOperator<BigIntegerCycleBuilder> setting) {
        BigIntegerAbstractCycle cycle = (BigIntegerAbstractCycle) setting.apply(Cycle.bigIntegerCycleBuilder()).giveRange($0, $1).giveStep($1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo($0);

        cycle = (BigIntegerAbstractCycle) setting.apply(Cycle.bigIntegerCycleBuilder()).giveRange($0, $1).giveStep($_1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo($1);
    }

    public void next(UnaryOperator<BigIntegerCycleBuilder> setting) {
        Supplier<BigIntegerCycleBuilder> builder = () -> setting.apply(Cycle.bigIntegerCycleBuilder()).giveRange($0, $2).giveInitValue($1);

        { // step > 0 + getAndNext
            BigIntegerCycle cycle = builder.get().giveStep($1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($1); // current is 2
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($2); // current is 1
            AssertionsEx.assertThat(cycle.get()).isEqualTo($0);
        }

        { // step > 0 + nextAndGet
            BigIntegerCycle cycle = builder.get().giveStep($1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($2); // current is 2
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($0); // current is 0
            AssertionsEx.assertThat(cycle.get()).isEqualTo($0);
        }

        { // step < 0 + getAndNext
            BigIntegerCycle cycle = builder.get().giveStep($_1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($1); // current is 0
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($0); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo($2);
        }

        { // step < 0 + nextAndGet
            BigIntegerCycle cycle = builder.get().giveStep($_1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($0); // current is 0
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($2); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo($2);
        }
    }

}
