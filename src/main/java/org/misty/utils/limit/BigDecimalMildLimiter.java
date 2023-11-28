package org.misty.utils.limit;

import java.math.BigDecimal;

public class BigDecimalMildLimiter extends MildLimiter<BigDecimalLimiter> {

    public BigDecimalMildLimiter(BigDecimalLimiter limiter) {
        super(limiter);
    }

    public BigDecimal get() {
        return super.getLimiter().get();
    }

    public boolean set(BigDecimal value) {
        try {
            super.getLimiter().set(value);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean plus(BigDecimal plus) {
        try {
            super.getLimiter().plus(plus);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean minus(BigDecimal minus) {
        try {
            super.getLimiter().minus(minus);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean increment() {
        try {
            super.getLimiter().increment();
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean decrement() {
        try {
            super.getLimiter().decrement();
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

}
