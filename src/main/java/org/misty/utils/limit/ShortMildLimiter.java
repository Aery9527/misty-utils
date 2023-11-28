package org.misty.utils.limit;

public class ShortMildLimiter extends MildLimiter<ShortLimiter> {

    public ShortMildLimiter(ShortLimiter limiter) {
        super(limiter);
    }

    public short get() {
        return super.getLimiter().get();
    }

    public boolean set(short value) {
        try {
            super.getLimiter().set(value);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean plus(short plus) {
        try {
            super.getLimiter().plus(plus);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean minus(short minus) {
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
