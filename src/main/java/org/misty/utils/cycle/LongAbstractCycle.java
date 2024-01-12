package org.misty.utils.cycle;

import org.misty.utils.fi.LongSupplierEx;
import org.misty.utils.range.LongRange;
import org.misty.utils.range.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LongAbstractCycle extends AbstractCycle implements LongCycle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final long step;

    private final LongRange range;

    private final LongSupplierEx start;

    public LongAbstractCycle(LongCycleBuilder builder) {
        super(builder.getTracked());
        this.step = builder.getStep();
        this.range = Range.longRangeBuilder().build(builder.getMin(), builder.getMax());
        this.start = step > 0 ? range::getLower : range::getUpper;
    }

    public LongRange getRange() {
        return range;
    }

    public LongSupplierEx getStart() {
        return start;
    }

    public long next(long oldValue) {
        long newValue = oldValue + this.step;
        if (this.range.inRange(newValue)) {
            return newValue;
        } else {
            long start = this.start.execute();
            this.logger.info(this + String.format(CYCLE_MSG_FORMAT, start));
            return start;
        }
    }

    @Override
    public String toString() {
        return super.toString(getMin(), getMax(), getStep());
    }

    @Override
    public long getStep() {
        return this.step;
    }

    @Override
    public long getMin() {
        return this.range.getLower();
    }

    @Override
    public long getMax() {
        return this.range.getUpper();
    }

}
