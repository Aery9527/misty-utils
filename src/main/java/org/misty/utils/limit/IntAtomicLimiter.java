package org.misty.utils.limit;

import java.util.concurrent.atomic.AtomicInteger;

public class IntAtomicLimiter extends IntAbstractLimiter {

    private final AtomicInteger value = new AtomicInteger();

    public IntAtomicLimiter(IntLimitVerifierHandler verifier, int initValue) {
        super(verifier);
        set(initValue);
    }

    @Override
    public int get() {
        return this.value.get();
    }

    @Override
    public void set(int value) {
        super.getVerifier().verifySet(value);
        this.value.set(value);
    }

    @Override
    public int plus(int plus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyPlus(current, plus));
    }

    @Override
    public int minus(int minus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyMinus(current, minus));
    }

}
