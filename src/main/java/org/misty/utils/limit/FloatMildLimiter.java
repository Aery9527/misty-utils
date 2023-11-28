package org.misty.utils.limit;

public class FloatMildLimiter extends MildLimiter<FloatLimiter> {

    public FloatMildLimiter(FloatLimiter limiter) {
        super(limiter);
    }

    public float get() {
        return super.getLimiter().get();
    }

    public boolean set(float value) {
        try {
            super.getLimiter().set(value);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean plus(float plus) {
        try {
            super.getLimiter().plus(plus);
            return MildLimiter.UPDATE_SUCCESS;
        } catch (MildException e) {
            return MildLimiter.UPDATE_FAIL;
        }
    }

    public boolean minus(float minus) {
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
