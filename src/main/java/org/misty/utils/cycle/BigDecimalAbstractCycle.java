package org.misty.utils.cycle;

import org.misty.utils.fi.SupplierEx;
import org.misty.utils.range.BigDecimalRange;
import org.misty.utils.range.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class BigDecimalAbstractCycle extends AbstractCycle implements BigDecimalCycle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final int scale;

    private final RoundingMode roundingMode;

    private final BigDecimal step;

    private final BigDecimalRange range;

    private final SupplierEx<BigDecimal> start;

    public BigDecimalAbstractCycle(BigDecimalCycleBuilder builder) {
        super(builder.getTracked());
        this.scale = builder.getScale();
        this.roundingMode = builder.getRoundingMode();
        this.step = builder.getStep();
        this.range = Range.bigDecimalRangeBuilder().build(builder.getMin(), builder.getMax());
        this.start = step.compareTo(BigDecimal.ZERO) > 0 ? range::getLower : range::getUpper;
    }

    public BigDecimalRange getRange() {
        return range;
    }

    public SupplierEx<BigDecimal> getStart() {
        return start;
    }

    public BigDecimal next(BigDecimal oldValue) {
        BigDecimal newValue = oldValue.add(this.step).setScale(this.scale, this.roundingMode);
        if (this.range.inRange(newValue)) {
            return newValue;
        } else {
            BigDecimal start = this.start.execute();
            this.logger.info(this + String.format(CYCLE_MSG_FORMAT, start));
            return start;
        }
    }

    @Override
    public String toString() {
        return super.toString(getMin(), getMax(), getStep());
    }

    @Override
    public BigDecimal getStep() {
        return this.step;
    }

    @Override
    public BigDecimal getMin() {
        return this.range.getLower();
    }

    @Override
    public BigDecimal getMax() {
        return this.range.getUpper();
    }

}
