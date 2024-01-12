package org.misty.utils.cycle;

import org.misty.utils.fi.IntSupplierEx;
import org.misty.utils.range.IntRange;
import org.misty.utils.range.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IntAbstractCycle extends AbstractCycle implements IntCycle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final int step;

    private final IntRange range;

    private final IntSupplierEx start;

    public IntAbstractCycle(IntCycleBuilder builder) {
        super(builder.getTracked());
        this.step = builder.getStep();
        this.range = Range.intRangeBuilder().build(builder.getMin(), builder.getMax());
        this.start = step > 0 ? range::getLower : range::getUpper;
    }

    public IntRange getRange() {
        return range;
    }

    public IntSupplierEx getStart() {
        return start;
    }

    public int next(int oldValue) {
        int newValue = oldValue + this.step;
        if (this.range.inRange(newValue)) {
            return newValue;
        } else {
            int start = this.start.execute();
            this.logger.info(this + String.format(CYCLE_MSG_FORMAT, start));
            return start;
        }
    }

    @Override
    public String toString() {
        return super.toString(getMin(), getMax(), getStep());
    }

    @Override
    public int getStep() {
        return this.step;
    }

    @Override
    public int getMin() {
        return this.range.getLower();
    }

    @Override
    public int getMax() {
        return this.range.getUpper();
    }

}
