package org.misty.utils.cycle;

import org.misty.utils.Tracked;
import org.misty.utils.limit.Limiter;
import org.misty.utils.limit.ShortLimiter;
import org.misty.utils.verify.Verifier;

public class ShortCycleBuilder extends AbstractCycleBuilder<ShortCycle, ShortCycleBuilder> {

    private ShortLimiter handle;

    private short step = 1;

    public ShortCycleBuilder(Tracked tracked) {
        super(tracked);
        giveStep((short) 1);
    }

    public ShortCycleBuilder giveRange(short min, short max) {
        Verifier.refuseNumber("min", min, "max", max);
        short initValue = this.handle == null ? min : this.handle.get();
        this.handle = Limiter.shortLimiterBuilder(getClass().getSimpleName())
                .giveLimit(min, max)
                .build(initValue);
        return this;
    }

    public ShortCycleBuilder giveInitValue(short initValue) {
        Verifier.refuseNull("handle", this.handle, msg -> {
            throw new IllegalStateException("please invoke #giveRange first");
        });

        this.handle.set(initValue);
        return this;
    }

    public ShortCycleBuilder giveStep(short step) {
        Verifier.refuseNumber("step", step, 0);
        this.step = step;
        return this;
    }

    @Override
    protected void buildVerify() {
        Verifier.refuseNull("handle", this.handle, msg -> {
            throw new IllegalStateException("please invoke #giveRange first");
        });
    }

    @Override
    protected ShortCycle buildBaseCycle() {
        return new ShortBaseCycle(this);
    }

    @Override
    protected ShortCycle buildVolatileCycle() {
        return new ShortVolatileCycle(this);
    }

    @Override
    protected ShortCycle buildAtomicCycle() {
        return new ShortAtomicCycle(this);
    }

    public short getInitValue() {
        return this.handle.get();
    }

    public short getStep() {
        return step;
    }

    public short getMin() {
        return this.handle.getMin().get();
    }

    public short getMax() {
        return this.handle.getMax().get();
    }

}
