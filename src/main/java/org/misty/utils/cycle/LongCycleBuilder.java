package org.misty.utils.cycle;

import org.misty.utils.Tracked;
import org.misty.utils.limit.Limiter;
import org.misty.utils.limit.LongLimiter;
import org.misty.utils.verify.Verifier;

public class LongCycleBuilder extends AbstractCycleBuilder<LongCycle, LongCycleBuilder> {

    private LongLimiter handle;

    private long step = 1;

    public LongCycleBuilder(Tracked tracked) {
        super(tracked);
        giveStep(1);
    }

    public LongCycleBuilder giveRange(long min, long max) {
        Verifier.refuseNumber("min", min, "max", max);
        long initValue = this.handle == null ? min : this.handle.get();
        this.handle = Limiter.longLimiterBuilder(getClass().getSimpleName())
                .giveLimit(min, max)
                .build(initValue);
        return this;
    }

    public LongCycleBuilder giveInitValue(long initValue) {
        Verifier.refuseNull("handle", this.handle, msg -> {
            throw new IllegalStateException("please invoke #giveRange first");
        });

        this.handle.set(initValue);
        return this;
    }

    public LongCycleBuilder giveStep(long step) {
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
    protected LongCycle buildBaseCycle() {
        return new LongBaseCycle(this);
    }

    @Override
    protected LongCycle buildVolatileCycle() {
        return new LongVolatileCycle(this);
    }

    @Override
    protected LongCycle buildAtomicCycle() {
        return new LongAtomicCycle(this);
    }

    public long getInitValue() {
        return this.handle.get();
    }

    public long getStep() {
        return step;
    }

    public long getMin() {
        return this.handle.getMin().get();
    }

    public long getMax() {
        return this.handle.getMax().get();
    }

}
