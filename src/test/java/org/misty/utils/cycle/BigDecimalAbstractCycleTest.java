package org.misty.utils.cycle;

import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BigDecimalAbstractCycleTest {

    private final BigDecimal $_2 = BigDecimal.valueOf(-2).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE);

    private final BigDecimal $_1 = BigDecimal.valueOf(-1).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE);

    private final BigDecimal $0 = BigDecimal.valueOf(0).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE);

    private final BigDecimal $1 = BigDecimal.valueOf(1).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE);

    private final BigDecimal $2 = BigDecimal.valueOf(2).setScale(BigDecimalCycle.DEFAULT_SCALE, BigDecimalCycle.DEFAULT_ROUNDING_MODE);

    public void range(UnaryOperator<BigDecimalCycleBuilder> setting) {
        BigDecimalCycle cycle = setting.apply(Cycle.bigDecimalCycleBuilder()).giveRange($_2, $2).build();
        AssertionsEx.assertThat(cycle.getMin()).isEqualTo($_2);
        AssertionsEx.assertThat(cycle.getMax()).isEqualTo($2);
    }

    public void start(UnaryOperator<BigDecimalCycleBuilder> setting) {
        BigDecimalAbstractCycle cycle = (BigDecimalAbstractCycle) setting.apply(Cycle.bigDecimalCycleBuilder()).giveRange($0, $1).giveStep($1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo($0);

        cycle = (BigDecimalAbstractCycle) setting.apply(Cycle.bigDecimalCycleBuilder()).giveRange($0, $1).giveStep($_1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo($1);
    }

    public void next(UnaryOperator<BigDecimalCycleBuilder> setting) {
        Supplier<BigDecimalCycleBuilder> builder = () -> setting.apply(Cycle.bigDecimalCycleBuilder()).giveRange($0, $2).giveInitValue($1);

        { // step > 0 + getAndNext
            BigDecimalCycle cycle = builder.get().giveStep($1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($1); // current is 2
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($2); // current is 1
            AssertionsEx.assertThat(cycle.get()).isEqualTo($0);
        }

        { // step > 0 + nextAndGet
            BigDecimalCycle cycle = builder.get().giveStep($1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($2); // current is 2
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($0); // current is 0
            AssertionsEx.assertThat(cycle.get()).isEqualTo($0);
        }

        { // step < 0 + getAndNext
            BigDecimalCycle cycle = builder.get().giveStep($_1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($1); // current is 0
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo($0); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo($2);
        }

        { // step < 0 + nextAndGet
            BigDecimalCycle cycle = builder.get().giveStep($_1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo($1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($0); // current is 0
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo($2); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo($2);
        }
    }

}
