package org.misty.utils.cycle;

import org.misty.utils.Tracked;
import org.misty.utils.limit.IntLimiter;
import org.misty.utils.limit.Limiter;
import org.misty.utils.verify.Verifier;

public class IntCycleBuilder extends AbstractCycleBuilder<IntCycle, IntCycleBuilder> {

    private IntLimiter handle;

    private int step = 1;

    public IntCycleBuilder(Tracked tracked) {
        super(tracked);
        giveStep(1);
    }

    public IntCycleBuilder giveRange(int min, int max) {
        Verifier.refuseNumber("min", min, "max", max);
        int initValue = this.handle == null ? min : this.handle.get();
        this.handle = Limiter.intLimiterBuilder(getClass().getSimpleName())
                .giveLimit(min, max)
                .build(initValue);
        return this;
    }

    public IntCycleBuilder giveInitValue(int initValue) {
        Verifier.refuseNull("handle", this.handle, msg -> {
            throw new IllegalStateException("please invoke #giveRange first");
        });

        this.handle.set(initValue);
        return this;
    }

    public IntCycleBuilder giveStep(int step) {
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
    protected IntCycle buildBaseCycle() {
        return new IntBaseCycle(this);
    }

    @Override
    protected IntCycle buildVolatileCycle() {
        return new IntVolatileCycle(this);
    }

    @Override
    protected IntCycle buildAtomicCycle() {
        return new IntAtomicCycle(this);
    }

    public int getInitValue() {
        return this.handle.get();
    }

    public int getStep() {
        return step;
    }

    public int getMin() {
        return this.handle.getMin();
    }

    public int getMax() {
        return this.handle.getMax();
    }

}
