package org.misty.utils.cycle;

import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.fi.SupplierEx;
import org.misty.utils.range.BigIntegerRange;
import org.misty.utils.range.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public abstract class BigIntegerAbstractCycle extends AbstractCycle implements BigIntegerCycle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BigInteger step;

    private final BigIntegerRange range;

    private final SupplierEx<BigInteger> start;

    private final ConsumerEx<BigInteger> cyclePrinter;

    public BigIntegerAbstractCycle(BigIntegerCycleBuilder builder) {
        super(builder.getTracked());
        this.step = builder.getStep();
        this.range = Range.bigIntegerRangeBuilder().build(builder.getMin(), builder.getMax());
        this.start = step.compareTo(BigInteger.ZERO) > 0 ? range::getLower : range::getUpper;
        this.cyclePrinter = builder.isPrintCycle() ? start -> {
            this.logger.info(this + String.format(CYCLE_MSG_FORMAT, start));
        } : start -> {
        };
    }

    public BigIntegerRange getRange() {
        return range;
    }

    public SupplierEx<BigInteger> getStart() {
        return start;
    }

    public BigInteger next(BigInteger oldValue) {
        BigInteger newValue = oldValue.add(this.step);
        if (this.range.inRange(newValue)) {
            return newValue;
        } else {
            BigInteger start = this.start.execute();
            this.cyclePrinter.execute(start);
            return start;
        }
    }

    @Override
    public String toString() {
        return super.toString(getMin(), getMax(), getStep());
    }

    @Override
    public BigInteger getStep() {
        return this.step;
    }

    @Override
    public BigInteger getMin() {
        return this.range.getLower();
    }

    @Override
    public BigInteger getMax() {
        return this.range.getUpper();
    }

}
