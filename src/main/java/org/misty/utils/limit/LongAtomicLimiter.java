package org.misty.utils.limit;

import java.util.concurrent.atomic.AtomicLong;

public class LongAtomicLimiter extends LongAbstractLimiter {

    private final AtomicLong value = new AtomicLong();

    public LongAtomicLimiter(LongLimitVerifierHandler verifier, long min, long max, long initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public long get() {
        return this.value.get();
    }

    @Override
    public void set(long value) {
        super.getVerifier().verifySet(value);
        this.value.set(value);
    }

    @Override
    public long plus(long plus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyPlus(current, plus));
    }

    @Override
    public long minus(long minus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyMinus(current, minus));
    }

}
