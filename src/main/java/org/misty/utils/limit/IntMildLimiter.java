package org.misty.utils.limit;

public class IntMildLimiter extends MildLimiter<IntLimiter> {

    public IntMildLimiter(IntLimiter limiter) {
        super(limiter);
    }

    public int get() {
        return super.getLimiter().get();
    }

    public boolean set(int value) {
        try {
            super.getLimiter().set(value);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildLimiter.MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean plus(int plus) {
        try {
            super.getLimiter().plus(plus);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildLimiter.MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean minus(int minus) {
        try {
            super.getLimiter().minus(minus);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildLimiter.MildException e) {
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
