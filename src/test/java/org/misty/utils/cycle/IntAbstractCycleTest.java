package org.misty.utils.cycle;

import org.misty._utils.AssertionsEx;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class IntAbstractCycleTest {

    public void range(UnaryOperator<IntCycleBuilder> setting) {
        IntCycle cycle = setting.apply(Cycle.intCycleBuilder()).giveRange(5566, 9527).build();
        AssertionsEx.assertThat(cycle.getMin()).isEqualTo(5566);
        AssertionsEx.assertThat(cycle.getMax()).isEqualTo(9527);
    }

    public void start(UnaryOperator<IntCycleBuilder> setting) {
        IntAbstractCycle cycle = (IntAbstractCycle) setting.apply(Cycle.intCycleBuilder()).giveRange(0, 1).giveStep(1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo(0);

        cycle = (IntAbstractCycle) setting.apply(Cycle.intCycleBuilder()).giveRange(0, 1).giveStep(-1).build();
        AssertionsEx.assertThat(cycle.getStart().execute()).isEqualTo(1);
    }

    public void next(UnaryOperator<IntCycleBuilder> setting) {
        Supplier<IntCycleBuilder> builder = () -> setting.apply(Cycle.intCycleBuilder()).giveRange(0, 2).giveInitValue(1);

        { // step > 0 + getAndNext
            IntCycle cycle = builder.get().giveStep(1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo(1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo(1); // current is 2
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo(2); // current is 1
            AssertionsEx.assertThat(cycle.get()).isEqualTo(0);
        }

        { // step > 0 + nextAndGet
            IntCycle cycle = builder.get().giveStep(1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo(1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo(2); // current is 2
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo(0); // current is 0
            AssertionsEx.assertThat(cycle.get()).isEqualTo(0);
        }

        { // step < 0 + getAndNext
            IntCycle cycle = builder.get().giveStep(-1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo(1);
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo(1); // current is 0
            AssertionsEx.assertThat(cycle.getAndNext()).isEqualTo(0); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo(2);
        }

        { // step < 0 + nextAndGet
            IntCycle cycle = builder.get().giveStep(-1).build();

            AssertionsEx.assertThat(cycle.get()).isEqualTo(1);
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo(0); // current is 0
            AssertionsEx.assertThat(cycle.nextAndGet()).isEqualTo(2); // current is 2
            AssertionsEx.assertThat(cycle.get()).isEqualTo(2);
        }
    }

}
