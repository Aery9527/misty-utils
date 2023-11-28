package org.misty.utils.limit;

public interface Limiter {

    class ErrorMsgFormat {

        public static final String SET_TERM = "set";

        public static final String MAX_LIMIT_TERM = "maxLimit";

        public static final String MIN_LIMIT_TERM = "minLimit";

        public static final String RESULT_TERM = "result";

        public static final String OVERFLOW_SHORT_MAX = "? that is overflow (MAX 2^15-1=" + Short.MAX_VALUE + ")";

        public static final String OVERFLOW_SHORT_MIN = "? that is overflow (MIN -2^15=" + Short.MIN_VALUE + ")";

        public static final String OVERFLOW_INTEGER_MAX = "? that is overflow (MAX 2^31-1=" + Integer.MAX_VALUE + ")";

        public static final String OVERFLOW_INTEGER_MIN = "? that is overflow (MIN -2^31=" + Integer.MIN_VALUE + ")";

        public static final String OVERFLOW_LONG_MAX = "? that is overflow (MAX 2^63-1=" + Long.MAX_VALUE + ")";

        public static final String OVERFLOW_LONG_MIN = "? that is overflow (MIN -2^63=" + Long.MIN_VALUE + ")";

        public static final String OVERFLOW_FLOAT = "target=%s, %s=%s, not allow Infinite(?/0) or NaN(0/0) in formula.";

        public static final String OPERATION = "%s(%s) %s %s = %s";
    }

    LimiterFactory INSTANCE = errorMsg -> {
        throw new IllegalArgumentException(errorMsg);
    };

    static ShortLimiterBuilder shortLimiterBuilder(String term) {
        return INSTANCE.shortLimiterBuilder(term);
    }

    static IntLimiterBuilder intLimiterBuilder(String term) {
        return INSTANCE.intLimiterBuilder(term);
    }

    static LongLimiterBuilder longLimiterBuilder(String term) {
        return INSTANCE.longLimiterBuilder(term);
    }

    static FloatLimiterBuilder floatLimiterBuilder(String term) {
        return INSTANCE.floatLimiterBuilder(term);
    }

    static DoubleLimiterBuilder doubleLimiterBuilder(String term) {
        return INSTANCE.doubleLimiterBuilder(term);
    }

    static BigDecimalLimiterBuilder bigDecimalBuilder(String term) {
        return INSTANCE.bigDecimalBuilder(term);
    }

    static BigIntegerLimiterBuilder bigIntegerBuilder(String term) {
        return INSTANCE.bigIntegerBuilder(term);
    }

}
