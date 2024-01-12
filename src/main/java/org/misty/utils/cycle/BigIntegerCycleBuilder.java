package org.misty.utils.cycle;

import org.misty.utils.Tracked;
import org.misty.utils.limit.BigIntegerLimiter;
import org.misty.utils.limit.Limiter;
import org.misty.utils.verify.Verifier;

import java.math.BigInteger;

public class BigIntegerCycleBuilder extends AbstractCycleBuilder<BigIntegerCycle, BigIntegerCycleBuilder> {

    private BigIntegerLimiter handle;

    private BigInteger step = BigInteger.ONE;

    public BigIntegerCycleBuilder(Tracked tracked) {
        super(tracked);
        giveStep(BigInteger.ONE);
    }

    public BigIntegerCycleBuilder giveRange(BigInteger min, BigInteger max) {
        Verifier.refuseNumber("min", min, "max", max);
        BigInteger initValue = this.handle == null ? min : this.handle.get();

        this.handle = Limiter.bigIntegerBuilder(getClass().getSimpleName())
                .giveLimit(min, max)
                .build(initValue);
        return this;
    }

    public BigIntegerCycleBuilder giveInitValue(BigInteger initValue) {
        Verifier.refuseNull("handle", this.handle, msg -> {
            throw new IllegalStateException("please invoke #giveRange first");
        });

        this.handle.set(initValue);
        return this;
    }

    public BigIntegerCycleBuilder giveStep(BigInteger step) {
        Verifier.refuseNumber("step", step, BigInteger.ZERO);
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
    protected BigIntegerCycle buildBaseCycle() {
        return new BigIntegerBaseCycle(this);
    }

    @Override
    protected BigIntegerCycle buildVolatileCycle() {
        return new BigIntegerVolatileCycle(this);
    }

    @Override
    protected BigIntegerCycle buildAtomicCycle() {
        return new BigIntegerAtomicCycle(this);
    }

    public BigInteger getInitValue() {
        return this.handle.get();
    }

    public BigInteger getStep() {
        return step;
    }

    public BigInteger getMin() {
        return this.handle.getMin();
    }

    public BigInteger getMax() {
        return this.handle.getMax();
    }

}
