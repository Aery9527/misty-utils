package org.misty.utils.cycle;

import org.misty.utils.Tracked;

public abstract class AbstractCycle implements Cycle {

    public static final String CYCLE_MSG_FORMAT = " reach range limit, cycle to %s.";

    private final Tracked tracked;

    public AbstractCycle(Tracked tracked) {
        this.tracked = tracked;
    }

    public <Type extends Number> String toString(Type min, Type max, Type step) {
        return this.tracked.say("[" + min + " to " + max + " by " + step + "]");
    }

    @Override
    public Tracked getTracked() {
        return this.tracked;
    }

}
