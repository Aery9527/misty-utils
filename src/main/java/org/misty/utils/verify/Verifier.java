package org.misty.utils.verify;

import java.math.BigDecimal;

public class Verifier {

    /**
     * 若有想丟出預設全局的exception, 就實作此interface的{@link #defaultThrown()}即可
     */
    public interface Logic<DefaultExceptionType extends Exception> {

        <TargetType> VerifierThrown<TargetType, DefaultExceptionType> defaultThrown();

        default void refuseNullOrEmpty(String term, Object arg) throws DefaultExceptionType {
            refuseNullOrEmpty(term, arg, defaultThrown());
        }

        default <TargetType, ExceptionType extends Exception> void refuseNullOrEmpty(
                String term,
                TargetType arg,
                VerifierThrown<TargetType, ExceptionType> thrown
        ) throws ExceptionType {
            if (Checker.isNullOrEmpty(arg)) {
                thrown.thrown(term, arg, "\"" + term + "\" can't be null or empty");
            }
        }

        default void requireShortMoreThanInclusive(
                String term,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortMoreThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortMoreThanInclusive(
                String term,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be more than or equal to %d", term, target, limit));
            }
        }

        default void requireShortMoreThanExclusive(
                String term,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortMoreThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortMoreThanExclusive(
                String term,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be more than %d", term, target, limit));
            }
        }

        default void requireShortLessThanInclusive(
                String term,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortLessThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortLessThanInclusive(
                String term,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be less than or equal to %d", term, target, limit));
            }
        }

        default void requireShortLessThanExclusive(
                String term,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortLessThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortLessThanExclusive(
                String term,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be less than %d", term, target, limit));
            }
        }

        default void requireIntMoreThanInclusive(
                String term,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntMoreThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntMoreThanInclusive(
                String term,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be more than or equal to %d", term, target, limit));
            }
        }

        default void requireIntMoreThanExclusive(
                String term,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntMoreThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntMoreThanExclusive(
                String term,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be more than %d", term, target, limit));
            }
        }

        default void requireIntLessThanInclusive(
                String term,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntLessThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntLessThanInclusive(
                String term,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be less than or equal to %d", term, target, limit));
            }
        }

        default void requireIntLessThanExclusive(
                String term,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntLessThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntLessThanExclusive(
                String term,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be less than %d", term, target, limit));
            }
        }

        default void requireLongMoreThanInclusive(
                String term,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongMoreThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongMoreThanInclusive(
                String term,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be more than or equal to %d", term, target, limit));
            }
        }

        default void requireLongMoreThanExclusive(
                String term,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongMoreThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongMoreThanExclusive(
                String term,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be more than %d", term, target, limit));
            }
        }

        default void requireLongLessThanInclusive(
                String term,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongLessThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongLessThanInclusive(
                String term,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be less than or equal to %d", term, target, limit));
            }
        }

        default void requireLongLessThanExclusive(
                String term,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongLessThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongLessThanExclusive(
                String term,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(term, target, String.format("\"%s(%d)\" must be less than %d", term, target, limit));
            }
        }

        default void requireFloatMoreThanInclusive(
                String term,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatMoreThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
                String term,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be more than or equal to %f", term, target, limit));
            }
        }

        default void requireFloatMoreThanExclusive(
                String term,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatMoreThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
                String term,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be more than %f", term, target, limit));
            }
        }

        default void requireFloatLessThanInclusive(
                String term,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatLessThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatLessThanInclusive(
                String term,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be less than or equal to %f", term, target, limit));
            }
        }

        default void requireFloatLessThanExclusive(
                String term,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatLessThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatLessThanExclusive(
                String term,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be less than %f", term, target, limit));
            }
        }

        default void requireDoubleMoreThanInclusive(
                String term,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleMoreThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
                String term,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be more than or equal to %f", term, target, limit));
            }
        }

        default void requireDoubleMoreThanExclusive(
                String term,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleMoreThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
                String term,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be more than %f", term, target, limit));
            }
        }

        default void requireDoubleLessThanInclusive(
                String term,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleLessThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
                String term,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be less than or equal to %f", term, target, limit));
            }
        }

        default void requireDoubleLessThanExclusive(
                String term,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleLessThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
                String term,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be less than %f", term, target, limit));
            }
        }

        default void requireBigDecimalMoreThanInclusive(
                String term,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalMoreThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
                String term,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) < 0) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be more than or equal to %f", term, target, limit));
            }
        }

        default void requireBigDecimalMoreThanExclusive(
                String term,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalMoreThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
                String term,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) <= 0) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be more than %f", term, target, limit));
            }
        }

        default void requireBigDecimalLessThanInclusive(
                String term,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalLessThanInclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
                String term,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) > 0) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be less than or equal to %f", term, target, limit));
            }
        }

        default void requireBigDecimalLessThanExclusive(
                String term,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalLessThanExclusive(term, target, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
                String term,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) >= 0) {
                thrown.thrown(term, target, String.format("\"%s(%f)\" must be less than %f", term, target, limit));
            }
        }

    }

    public static final Logic<IllegalArgumentException> INSTANCE = new Logic<IllegalArgumentException>() {
        @Override
        public <TargetType> VerifierThrown<TargetType, IllegalArgumentException> defaultThrown() {
            return (term, arg, errorMsg) -> {
                throw new IllegalArgumentException(errorMsg);
            };
        }
    };

    public static void refuseNullOrEmpty(String term, Object arg) throws IllegalArgumentException {
        INSTANCE.refuseNullOrEmpty(term, arg);
    }

    public static <TargetType, ExceptionType extends Exception> void refuseNullOrEmpty(
            String term,
            TargetType arg,
            VerifierThrown<TargetType, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNullOrEmpty(term, arg, thrown);
    }

    public static ShortRangeVerifier ofRange(short min, short max) {
        return new ShortRangeVerifier(min, max);
    }

    public static void requireShortMoreThanInclusive(
            String term,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String term,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanInclusive(term, target, limit, thrown);
    }

    public static void requireShortMoreThanExclusive(
            String term,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String term,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanExclusive(term, target, limit, thrown);
    }

    public static void requireShortLessThanInclusive(
            String term,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String term,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanInclusive(term, target, limit, thrown);
    }

    public static void requireShortLessThanExclusive(
            String term,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String term,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanExclusive(term, target, limit, thrown);
    }

    public static IntRangeVerifier ofRange(int min, int max) {
        return new IntRangeVerifier(min, max);
    }

    public static void requireIntMoreThanInclusive(
            String term,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String term,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanInclusive(term, target, limit, thrown);
    }

    public static void requireIntMoreThanExclusive(
            String term,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String term,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanExclusive(term, target, limit, thrown);
    }

    public static void requireIntLessThanInclusive(
            String term,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String term,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanInclusive(term, target, limit, thrown);
    }

    public static void requireIntLessThanExclusive(
            String term,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String term,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanExclusive(term, target, limit, thrown);
    }

    public static LongRangeVerifier ofRange(long min, long max) {
        return new LongRangeVerifier(min, max);
    }

    public static void requireLongMoreThanInclusive(
            String term,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String term,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanInclusive(term, target, limit, thrown);
    }

    public static void requireLongMoreThanExclusive(
            String term,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String term,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanExclusive(term, target, limit, thrown);
    }

    public static void requireLongLessThanInclusive(
            String term,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String term,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanInclusive(term, target, limit, thrown);
    }

    public static void requireLongLessThanExclusive(
            String term,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String term,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanExclusive(term, target, limit, thrown);
    }

    public static FloatRangeVerifier ofRange(float min, float max) {
        return new FloatRangeVerifier(min, max);
    }

    public static void requireFloatMoreThanInclusive(
            String term,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String term,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanInclusive(term, target, limit, thrown);
    }

    public static void requireFloatMoreThanExclusive(
            String term,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String term,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanExclusive(term, target, limit, thrown);
    }

    public static void requireFloatLessThanInclusive(
            String term,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String term,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanInclusive(term, target, limit, thrown);
    }

    public static void requireFloatLessThanExclusive(
            String term,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String term,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanExclusive(term, target, limit, thrown);
    }

    public static DoubleRangeVerifier ofRange(double min, double max) {
        return new DoubleRangeVerifier(min, max);
    }

    public static void requireDoubleMoreThanInclusive(
            String term,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String term,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanInclusive(term, target, limit, thrown);
    }

    public static void requireDoubleMoreThanExclusive(
            String term,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String term,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanExclusive(term, target, limit, thrown);
    }

    public static void requireDoubleLessThanInclusive(
            String term,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String term,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanInclusive(term, target, limit, thrown);
    }

    public static void requireDoubleLessThanExclusive(
            String term,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String term,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanExclusive(term, target, limit, thrown);
    }

    public static BigDecimalRangeVerifier ofRange(BigDecimal min, BigDecimal max) {
        return new BigDecimalRangeVerifier(min, max);
    }

    public static void requireBigDecimalMoreThanInclusive(
            String term,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String term,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanInclusive(term, target, limit, thrown);
    }

    public static void requireBigDecimalMoreThanExclusive(
            String term,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String term,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanExclusive(term, target, limit, thrown);
    }

    public static void requireBigDecimalLessThanInclusive(
            String term,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanInclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String term,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanInclusive(term, target, limit, thrown);
    }

    public static void requireBigDecimalLessThanExclusive(
            String term,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanExclusive(term, target, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String term,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanExclusive(term, target, limit, thrown);
    }

}
