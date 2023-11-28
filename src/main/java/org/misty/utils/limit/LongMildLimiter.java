package org.misty.utils.limit;

public class LongMildLimiter extends MildLimiter<LongLimiter> {

    public LongMildLimiter(LongLimiter limiter) {
        super(limiter);
    }

    public long get() {
        return super.getLimiter().get();
    }

    public boolean set(long value) {
        try {
            super.getLimiter().set(value);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean plus(long plus) {
        try {
            super.getLimiter().plus(plus);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean minus(long minus) {
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
