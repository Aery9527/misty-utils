package org.misty.utils.limit;

public abstract class MildLimiter<LimiterType extends Limiter> {

    protected static class MildException extends RuntimeException {
    }

    public static boolean UPDATE_SUCCESS = true;

    public static boolean UPDATE_FAIL = false;

    private final LimiterType limiter;

    public MildLimiter(LimiterType limiter) {
        this.limiter = limiter;
    }

    public LimiterType getLimiter() {
        return limiter;
    }
}
