package org.misty.utils.verify;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Verifier {

    public static class ErrorMsgFormat {

        public static final String REFUSE_NULL_NUMBER = "%s(%s) can't equals to %s(%s)";

        public static final String REFUSE_NULL = "\"%s\" can't be null";

        public static final String REFUSE_NULL_OR_EMPTY = "\"%s\" can't be null or empty";

        public static final String MORE_THAN_INCLUSIVE = "%s(%s) must >= %s(%s)";

        public static final String MORE_THAN_EXCLUSIVE = "%s(%s) must > %s(%s)";

        public static final String LESS_THAN_INCLUSIVE = "%s(%s) must <= %s(%s)";

        public static final String LESS_THAN_EXCLUSIVE = "%s(%s) must < %s(%s)";

        public static final String REQUIRE_RANGE_INCLUSIVE = "%s(%s) must in range [%s, %s] {target >= min && target <= max}";

        public static final String REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE = "%s(%s) must in range [%s, %s) {target >= min && target < max}";

        public static final String REQUIRE_RANGE_EXCLUSIVE = "%s(%s) must in range (%s, %s) {target > min && target < max}";

        public static final String REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE = "%s(%s) must in range (%s, %s] {target > min && target <= max}";

        public static final String REFUSE_RANGE_INCLUSIVE = "%s(%s) can't in range [%s, %s] {target < min || target > max}";

        public static final String REFUSE_RANGE_INCLUSIVE_EXCLUSIVE = "%s(%s) can't in range [%s, %s) {target < min || target => max}";

        public static final String REFUSE_RANGE_EXCLUSIVE = "%s(%s) can't in range (%s, %s) {target <= min || target => max}";

        public static final String REFUSE_RANGE_EXCLUSIVE_INCLUSIVE = "%s(%s) can't in range (%s, %s] {target <= min || target > max}";
    }

    public static final VerifierLogic<IllegalArgumentException> INSTANCE = new VerifierLogic<>() {

        @Override
        public <TargetType, MagType extends VerifierErrorMsg<TargetType>> VerifierThrown<TargetType, MagType, IllegalArgumentException> getThrower() {
            return error -> {
                throw new IllegalArgumentException(error.getErrorMsg());
            };
        }

        @Override
        public ShortRangeVerifier ofRange(short min, short max) throws IllegalArgumentException {
            return ofRange("", min, max);
        }

        @Override
        public ShortRangeVerifier ofRange(String title, short min, short max) throws IllegalArgumentException {
            return new ShortRangeVerifier(title, min, max);
        }

        @Override
        public IntRangeVerifier ofRange(int min, int max) throws IllegalArgumentException {
            return ofRange("", min, max);
        }

        @Override
        public IntRangeVerifier ofRange(String title, int min, int max) throws IllegalArgumentException {
            return new IntRangeVerifier(title, min, max);
        }

        @Override
        public LongRangeVerifier ofRange(long min, long max) throws IllegalArgumentException {
            return ofRange("", min, max);
        }

        @Override
        public LongRangeVerifier ofRange(String title, long min, long max) throws IllegalArgumentException {
            return new LongRangeVerifier(title, min, max);
        }

        @Override
        public FloatRangeVerifier ofRange(float min, float max) throws IllegalArgumentException {
            return ofRange("", min, max);
        }

        @Override
        public FloatRangeVerifier ofRange(String title, float min, float max) throws IllegalArgumentException {
            return new FloatRangeVerifier(title, min, max);
        }

        @Override
        public DoubleRangeVerifier ofRange(double min, double max) throws IllegalArgumentException {
            return ofRange("", min, max);
        }

        @Override
        public DoubleRangeVerifier ofRange(String title, double min, double max) throws IllegalArgumentException {
            return new DoubleRangeVerifier(title, min, max);
        }

        @Override
        public BigDecimalRangeVerifier ofRange(BigDecimal min, BigDecimal max) throws IllegalArgumentException {
            return ofRange("", min, max);
        }

        @Override
        public BigDecimalRangeVerifier ofRange(String title, BigDecimal min, BigDecimal max) throws IllegalArgumentException {
            return new BigDecimalRangeVerifier(title, min, max);
        }

        @Override
        public BigIntegerRangeVerifier ofRange(BigInteger min, BigInteger max) throws IllegalArgumentException {
            return ofRange("", min, max);
        }

        @Override
        public BigIntegerRangeVerifier ofRange(String title, BigInteger min, BigInteger max) throws IllegalArgumentException {
            return new BigIntegerRangeVerifier(title, min, max);
        }
    };

    public static void refuseNumber(String valueTerm, short value, short refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            short value,
            short refuseValue,
            VerifierThrown<Short, VerifierRefuseNumberErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, short value, String refuseTerm, short refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            short value,
            String refuseTerm,
            short refuseValue,
            VerifierThrown<Short, VerifierRefuseNumberErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, int value, int refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            int value,
            int refuseValue,
            VerifierThrown<Integer, VerifierRefuseNumberErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, int value, String refuseTerm, int refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            int value,
            String refuseTerm,
            int refuseValue,
            VerifierThrown<Integer, VerifierRefuseNumberErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, long value, long refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            long value,
            long refuseValue,
            VerifierThrown<Long, VerifierRefuseNumberErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, long value, String refuseTerm, long refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            long value,
            String refuseTerm,
            long refuseValue,
            VerifierThrown<Long, VerifierRefuseNumberErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, float value, float refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            float value,
            float refuseValue,
            VerifierThrown<Float, VerifierRefuseNumberErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, float value, String refuseTerm, float refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            float value,
            String refuseTerm,
            float refuseValue,
            VerifierThrown<Float, VerifierRefuseNumberErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, double value, double refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            double value,
            double refuseValue,
            VerifierThrown<Double, VerifierRefuseNumberErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, double value, String refuseTerm, double refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            double value,
            String refuseTerm,
            double refuseValue,
            VerifierThrown<Double, VerifierRefuseNumberErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, BigDecimal value, BigDecimal refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            BigDecimal value,
            BigDecimal refuseValue,
            VerifierThrown<BigDecimal, VerifierRefuseNumberErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, BigDecimal value, String refuseTerm, BigDecimal refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            BigDecimal value,
            String refuseTerm,
            BigDecimal refuseValue,
            VerifierThrown<BigDecimal, VerifierRefuseNumberErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, BigInteger value, BigInteger refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            BigInteger value,
            BigInteger refuseValue,
            VerifierThrown<BigInteger, VerifierRefuseNumberErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseValue, thrown);
    }

    public static void refuseNumber(String valueTerm, BigInteger value, String refuseTerm, BigInteger refuseValue) {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue);
    }

    public static <ExceptionType extends Exception> void refuseNumber(
            String valueTerm,
            BigInteger value,
            String refuseTerm,
            BigInteger refuseValue,
            VerifierThrown<BigInteger, VerifierRefuseNumberErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNumber(valueTerm, value, refuseTerm, refuseValue, thrown);
    }

    public static void refuseNull(String term, Object arg) throws IllegalArgumentException {
        INSTANCE.refuseNull(term, arg);
    }

    public static <TargetType, ExceptionType extends Exception> void refuseNull(
            String term,
            TargetType arg,
            VerifierThrown<TargetType, VerifierErrorMsg<TargetType>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNull(term, arg, thrown);
    }

    public static void refuseNullOrEmpty(String term, Object arg) throws IllegalArgumentException {
        INSTANCE.refuseNullOrEmpty(term, arg);
    }

    public static <TargetType, ExceptionType extends Exception> void refuseNullOrEmpty(
            String term,
            TargetType arg,
            VerifierThrown<TargetType, VerifierErrorMsg<TargetType>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNullOrEmpty(term, arg, thrown);
    }

    public static ShortRangeVerifier ofRange(short min, short max) {
        return (ShortRangeVerifier) INSTANCE.ofRange(min, max);
    }

    public static ShortRangeVerifier ofRange(String title, short min, short max) {
        return (ShortRangeVerifier) INSTANCE.ofRange(title, min, max);
    }

    public static void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireShortMoreThanInclusive(
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireShortMoreThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireShortMoreThanExclusive(
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireShortMoreThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireShortLessThanInclusive(
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireShortLessThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireShortLessThanExclusive(
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireShortLessThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static IntRangeVerifier ofRange(int min, int max) {
        return (IntRangeVerifier) INSTANCE.ofRange(min, max);
    }

    public static IntRangeVerifier ofRange(String title, int min, int max) {
        return (IntRangeVerifier) INSTANCE.ofRange(title, min, max);
    }

    public static void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireIntMoreThanInclusive(
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireIntMoreThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireIntMoreThanExclusive(
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireIntMoreThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireIntLessThanInclusive(
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireIntLessThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireIntLessThanExclusive(
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireIntLessThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static LongRangeVerifier ofRange(long min, long max) {
        return (LongRangeVerifier) INSTANCE.ofRange(min, max);
    }

    public static LongRangeVerifier ofRange(String title, long min, long max) {
        return (LongRangeVerifier) INSTANCE.ofRange(title, min, max);
    }

    public static void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireLongMoreThanInclusive(
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireLongMoreThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireLongMoreThanExclusive(
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireLongMoreThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireLongLessThanInclusive(
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireLongLessThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireLongLessThanExclusive(
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireLongLessThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static FloatRangeVerifier ofRange(float min, float max) {
        return (FloatRangeVerifier) INSTANCE.ofRange(min, max);
    }

    public static FloatRangeVerifier ofRange(String title, float min, float max) {
        return (FloatRangeVerifier) INSTANCE.ofRange(title, min, max);
    }

    public static void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireFloatMoreThanInclusive(
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireFloatMoreThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireFloatMoreThanExclusive(
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireFloatMoreThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireFloatLessThanInclusive(
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireFloatLessThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireFloatLessThanExclusive(
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireFloatLessThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static DoubleRangeVerifier ofRange(double min, double max) {
        return (DoubleRangeVerifier) INSTANCE.ofRange(min, max);
    }

    public static DoubleRangeVerifier ofRange(String title, double min, double max) {
        return (DoubleRangeVerifier) INSTANCE.ofRange(title, min, max);
    }

    public static void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireDoubleMoreThanInclusive(
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireDoubleMoreThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireDoubleMoreThanExclusive(
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireDoubleMoreThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireDoubleLessThanInclusive(
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireDoubleLessThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireDoubleLessThanExclusive(
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireDoubleLessThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static BigDecimalRangeVerifier ofRange(BigDecimal min, BigDecimal max) {
        return (BigDecimalRangeVerifier) INSTANCE.ofRange(min, max);
    }

    public static BigDecimalRangeVerifier ofRange(String title, BigDecimal min, BigDecimal max) {
        return (BigDecimalRangeVerifier) INSTANCE.ofRange(title, min, max);
    }

    public static void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalMoreThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigDecimalMoreThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalMoreThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigDecimalMoreThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalLessThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigDecimalLessThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalLessThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigDecimalLessThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static BigIntegerRangeVerifier ofRange(BigInteger min, BigInteger max) {
        return (BigIntegerRangeVerifier) INSTANCE.ofRange(min, max);
    }

    public static BigIntegerRangeVerifier ofRange(String title, BigInteger min, BigInteger max) {
        return (BigIntegerRangeVerifier) INSTANCE.ofRange(title, min, max);
    }

    public static void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerMoreThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerMoreThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanInclusive(title, targetTerm, target, limit);
    }

    public static void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanInclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanInclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanInclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanExclusive(title, targetTerm, target, limit);
    }

    public static void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigIntegerLessThanExclusive(title, targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanExclusive(title, targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigIntegerLessThanExclusive(title, targetTerm, target, limitTerm, limit, thrown);
    }

}
