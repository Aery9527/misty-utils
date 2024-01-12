package org.misty.utils.cycle;

import org.misty.utils.Tracked;
import org.misty.utils.limit.BigDecimalLimiter;
import org.misty.utils.limit.Limiter;
import org.misty.utils.verify.Verifier;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalCycleBuilder extends AbstractCycleBuilder<BigDecimalCycle, BigDecimalCycleBuilder> {

    private int scale = BigDecimalCycle.DEFAULT_SCALE;

    private RoundingMode roundingMode = BigDecimalCycle.DEFAULT_ROUNDING_MODE;

    private BigDecimalLimiter handle;

    private BigDecimal step = BigDecimal.ONE;

    public BigDecimalCycleBuilder(Tracked tracked) {
        super(tracked);
        giveStep(BigDecimal.ONE);
    }

    public BigDecimalCycleBuilder giveScale(int scale, RoundingMode roundingMode) {
        BigDecimal.ONE.setScale(scale, roundingMode); // pre-check
        this.scale = scale;
        this.roundingMode = roundingMode;
        return this;
    }

    public BigDecimalCycleBuilder giveRange(BigDecimal min, BigDecimal max) {
        min = min.setScale(this.scale, this.roundingMode);
        max = max.setScale(this.scale, this.roundingMode);

        Verifier.refuseNumber("min", min, "max", max);
        BigDecimal initValue = this.handle == null ? min : this.handle.get();

        this.handle = Limiter.bigDecimalBuilder(getClass().getSimpleName())
                .giveLimit(min, max)
                .build(initValue);
        return this;
    }

    public BigDecimalCycleBuilder giveInitValue(BigDecimal initValue) {
        Verifier.refuseNull("handle", this.handle, msg -> {
            throw new IllegalStateException("please invoke #giveRange first");
        });

        this.handle.set(initValue.setScale(this.scale, this.roundingMode));
        return this;
    }

    public BigDecimalCycleBuilder giveStep(BigDecimal step) {
        Verifier.refuseNumber("step", step, BigDecimal.ZERO);
        this.step = step.setScale(this.scale, this.roundingMode);
        return this;
    }

    @Override
    protected void buildVerify() {
        Verifier.refuseNull("handle", this.handle, msg -> {
            throw new IllegalStateException("please invoke #giveRange first");
        });
    }

    @Override
    protected BigDecimalCycle buildBaseCycle() {
        return new BigDecimalBaseCycle(this);
    }

    @Override
    protected BigDecimalCycle buildVolatileCycle() {
        return new BigDecimalVolatileCycle(this);
    }

    @Override
    protected BigDecimalCycle buildAtomicCycle() {
        return new BigDecimalAtomicCycle(this);
    }

    public int getScale() {
        return scale;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public BigDecimal getInitValue() {
        return this.handle.get();
    }

    public BigDecimal getStep() {
        return step;
    }

    public BigDecimal getMin() {
        return this.handle.getMin();
    }

    public BigDecimal getMax() {
        return this.handle.getMax();
    }

}
