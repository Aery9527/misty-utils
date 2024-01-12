package org.misty.utils.limit;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class BigDecimalAtomicLimiter extends BigDecimalAbstractLimiter {

    private final AtomicReference<BigDecimal> value = new AtomicReference<>();

    public BigDecimalAtomicLimiter(BigDecimalLimitVerifierHandler verifier, BigDecimal min, BigDecimal max, BigDecimal initValue) {
        super(verifier, min, max);
        set(initValue);
    }

    @Override
    public BigDecimal get() {
        return this.value.get();
    }

    @Override
    public void set(BigDecimal value) {
        this.value.set(super.getVerifier().verifySet(value));
    }

    @Override
    public BigDecimal plus(BigDecimal plus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyPlus(current, plus));
    }

    @Override
    public BigDecimal minus(BigDecimal minus) {
        return this.value.updateAndGet(current -> super.getVerifier().verifyMinus(current, minus));
    }

}
