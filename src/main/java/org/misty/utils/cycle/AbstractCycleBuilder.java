package org.misty.utils.cycle;

import org.misty.utils.Tracked;
import org.misty.utils.verify.Verifier;

public abstract class AbstractCycleBuilder<CycleType extends Cycle, Self extends AbstractCycleBuilder<CycleType, Self>> {

    public enum DeclareType {
        BASE, VOLATILE, ATOMIC,
    }

    private Tracked tracked;

    private DeclareType declareType = DeclareType.BASE;

    public AbstractCycleBuilder(Tracked tracked) {
        giveTracked(tracked);
    }

    public Self giveTracked(Tracked tracked) {
        Verifier.refuseNull("tracked", tracked);
        this.tracked = tracked;
        return (Self) this;
    }

    public Self withBase() {
        this.declareType = DeclareType.BASE;
        return (Self) this;
    }

    public Self withVolatile() {
        this.declareType = DeclareType.VOLATILE;
        return (Self) this;
    }

    public Self withAtomic() {
        this.declareType = DeclareType.ATOMIC;
        return (Self) this;
    }

    public CycleType build() {
        buildVerify();
        if (declareType == DeclareType.BASE) {
            return buildBaseCycle();
        } else if (declareType == DeclareType.VOLATILE) {
            return buildVolatileCycle();
        } else {
            return buildAtomicCycle();
        }
    }

    protected abstract void buildVerify();

    protected abstract CycleType buildBaseCycle();

    protected abstract CycleType buildVolatileCycle();

    protected abstract CycleType buildAtomicCycle();

    public Tracked getTracked() {
        return tracked;
    }

    public DeclareType getDeclareType() {
        return declareType;
    }

}
