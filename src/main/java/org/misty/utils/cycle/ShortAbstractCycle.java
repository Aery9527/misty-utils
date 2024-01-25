package org.misty.utils.cycle;

import org.misty.utils.fi.ShortConsumerEx;
import org.misty.utils.fi.ShortSupplierEx;
import org.misty.utils.range.Range;
import org.misty.utils.range.ShortRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ShortAbstractCycle extends AbstractCycle implements ShortCycle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final short step;

    private final ShortRange range;

    private final ShortSupplierEx start;

    private final ShortConsumerEx cyclePrinter;

    public ShortAbstractCycle(ShortCycleBuilder builder) {
        super(builder.getTracked());
        this.step = builder.getStep();
        this.range = Range.shortRangeBuilder().build(builder.getMin(), builder.getMax());
        this.start = step > 0 ? range::getLower : range::getUpper;
        this.cyclePrinter = builder.isPrintCycle() ? start -> {
            this.logger.info(this + String.format(CYCLE_MSG_FORMAT, start));
        } : start -> {
        };
    }

    public ShortRange getRange() {
        return range;
    }

    public ShortSupplierEx getStart() {
        return start;
    }

    public short next(short oldValue) {
        short newValue = (short) (oldValue + this.step);
        if (this.range.inRange(newValue)) {
            return newValue;
        } else {
            short start = this.start.execute();
            this.cyclePrinter.execute(start);
            return start;
        }
    }

    @Override
    public String toString() {
        return super.toString(getMin(), getMax(), getStep());
    }

    @Override
    public short getStep() {
        return this.step;
    }

    @Override
    public short getMin() {
        return this.range.getLower();
    }

    @Override
    public short getMax() {
        return this.range.getUpper();
    }

}
