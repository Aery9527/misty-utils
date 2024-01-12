package org.misty.utils.cycle;

import org.misty._utils.AssertionsEx;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ShortAbstractCycleTest {

    public void range(UnaryOperator<ShortCycleBuilder> setting) {
        ShortCycle cycle = setting.apply(Cycle.shortCycleBuilder()).giveRange((short) 5566, (short) 9527).build();
        AssertionsEx.assertThat(cycle.getMin()).isEqualTo((short) 5566);
        AssertionsEx.assertThat(cycle.getMax()).isEqualTo((short) 9527);
    }

    public void start(UnaryOperator<ShortCycleBuilder> setting) {
        ShortAbstractCycle cycle = (ShortAbstractCycle) setting.apply(Cycle.shortCycleBuilder()).giveRange((short) 0, (short) 1).giveStep((short) 1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo((short) 0);

        cycle = (ShortAbstractCycle) setting.apply(Cycle.shortCycleBuilder()).giveRange((short) 0, (short) 1).giveStep((short) -1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo((short) 1);
    }

    public void next(UnaryOperator<ShortCycleBuilder> setting) {
        Supplier<ShortCycleBuilder> builder = () -> setting.apply(Cycle.shortCycleBuilder()).giveRange((short) 0, (short) 2).giveInitValue((short) 1);

        { // step > 0 + getAndNext
            ShortCycle cycle = builder.get().giveStep((short) 1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo((short) 1); // current is 2
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo((short) 2); // current is 1
            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 0);
        }

        { // step > 0 + nextAndGet
            ShortCycle cycle = builder.get().giveStep((short) 1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo((short) 2); // current is 2
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo((short) 0); // current is 0
            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 0);
        }

        { // step < 0 + getAndNext
            ShortCycle cycle = builder.get().giveStep((short) -1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo((short) 1); // current is 0
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo((short) 0); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 2);
        }

        { // step < 0 + nextAndGet
            ShortCycle cycle = builder.get().giveStep((short) -1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo((short) 0); // current is 0
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo((short) 2); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo((short) 2);
        }
    }

}
