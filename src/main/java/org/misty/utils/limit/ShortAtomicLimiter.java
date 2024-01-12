package org.misty.utils.limit;

import java.util.concurrent.atomic.AtomicReference;

public class ShortAtomicLimiter extends ShortAbstractLimiter {

    private final AtomicReference<Short> value = new AtomicReference<>();

    public ShortAtomicLimiter(ShortLimitVerifierHandler verifier, short min, short max, short initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public short get() {
        return this.value.get();
    }

    @Override
    public void set(short value) {
        super.getVerifier().verifySet(value);
        this.value.set(value);
    }

    @Override
    public short plus(short plus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyPlus(current, plus));
    }

    @Override
    public short minus(short minus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyMinus(current, minus));
    }

}
