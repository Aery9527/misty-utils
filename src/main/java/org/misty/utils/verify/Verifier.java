package org.misty.utils.verify;

import java.math.BigDecimal;
import java.util.Optional;

public class Verifier {

    /**
     * 若有想丟出預設全局的exception, 就實作此interface的{@link #defaultThrown()}即可
     */
    public interface Logic<DefaultExceptionType extends Exception> {

        <TargetType> VerifierThrown<TargetType, DefaultExceptionType> defaultThrown();

        default void refuseNull(String term, Object arg) throws DefaultExceptionType {
            refuseNull(term, arg, defaultThrown());
        }

        default <TargetType, ExceptionType extends Exception> void refuseNull(
                String term,
                TargetType arg,
                VerifierThrown<TargetType, ExceptionType> thrown
        ) throws ExceptionType {
            if (arg == null || (arg instanceof Optional && !((Optional) arg).isPresent())) {
                thrown.thrown(term, arg, String.format(ErrorMsgFormat.REFUSE_NULL, term));
            }
        }

        default void refuseNullOrEmpty(String term, Object arg) throws DefaultExceptionType {
            refuseNullOrEmpty(term, arg, defaultThrown());
        }

        default <TargetType, ExceptionType extends Exception> void refuseNullOrEmpty(
                String term,
                TargetType arg,
                VerifierThrown<TargetType, ExceptionType> thrown
        ) throws ExceptionType {
            if (Checker.isNullOrEmpty(arg)) {
                thrown.thrown(term, arg, String.format(ErrorMsgFormat.REFUSE_NULL_OR_EMPTY, term));
            }
        }

        default void requireShortMoreThanInclusive(
                String targetTerm,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortMoreThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireShortMoreThanInclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit
        ) throws DefaultExceptionType {
            requireShortMoreThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortMoreThanInclusive(
                String targetTerm,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            requireShortMoreThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireShortMoreThanInclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireShortMoreThanExclusive(
                String targetTerm,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortMoreThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireShortMoreThanExclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit
        ) throws DefaultExceptionType {
            requireShortMoreThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortMoreThanExclusive(
                String targetTerm,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            requireShortMoreThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireShortMoreThanExclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireShortLessThanInclusive(
                String targetTerm,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortLessThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireShortLessThanInclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit
        ) throws DefaultExceptionType {
            requireShortLessThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortLessThanInclusive(
                String targetTerm,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            requireShortLessThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireShortLessThanInclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireShortLessThanExclusive(
                String targetTerm,
                short target,
                short limit
        ) throws DefaultExceptionType {
            requireShortLessThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireShortLessThanExclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit
        ) throws DefaultExceptionType {
            requireShortLessThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireShortLessThanExclusive(
                String targetTerm,
                short target,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            requireShortLessThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireShortLessThanExclusive(
                String targetTerm,
                short target,
                String limitTerm,
                short limit,
                VerifierThrown<Short, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireIntMoreThanInclusive(
                String targetTerm,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntMoreThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireIntMoreThanInclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit
        ) throws DefaultExceptionType {
            requireIntMoreThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntMoreThanInclusive(
                String targetTerm,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            requireIntMoreThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireIntMoreThanInclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireIntMoreThanExclusive(
                String targetTerm,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntMoreThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireIntMoreThanExclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit
        ) throws DefaultExceptionType {
            requireIntMoreThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntMoreThanExclusive(
                String targetTerm,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            requireIntMoreThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireIntMoreThanExclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireIntLessThanInclusive(
                String targetTerm,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntLessThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireIntLessThanInclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit
        ) throws DefaultExceptionType {
            requireIntLessThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntLessThanInclusive(
                String targetTerm,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            requireIntLessThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireIntLessThanInclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireIntLessThanExclusive(
                String targetTerm,
                int target,
                int limit
        ) throws DefaultExceptionType {
            requireIntLessThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireIntLessThanExclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit
        ) throws DefaultExceptionType {
            requireIntLessThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireIntLessThanExclusive(
                String targetTerm,
                int target,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            requireIntLessThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireIntLessThanExclusive(
                String targetTerm,
                int target,
                String limitTerm,
                int limit,
                VerifierThrown<Integer, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireLongMoreThanInclusive(
                String targetTerm,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongMoreThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireLongMoreThanInclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit
        ) throws DefaultExceptionType {
            requireLongMoreThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongMoreThanInclusive(
                String targetTerm,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            requireLongMoreThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireLongMoreThanInclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireLongMoreThanExclusive(
                String targetTerm,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongMoreThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireLongMoreThanExclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit
        ) throws DefaultExceptionType {
            requireLongMoreThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongMoreThanExclusive(
                String targetTerm,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            requireLongMoreThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireLongMoreThanExclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireLongLessThanInclusive(
                String targetTerm,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongLessThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireLongLessThanInclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit
        ) throws DefaultExceptionType {
            requireLongLessThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongLessThanInclusive(
                String targetTerm,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            requireLongLessThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireLongLessThanInclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireLongLessThanExclusive(
                String targetTerm,
                long target,
                long limit
        ) throws DefaultExceptionType {
            requireLongLessThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireLongLessThanExclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit
        ) throws DefaultExceptionType {
            requireLongLessThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireLongLessThanExclusive(
                String targetTerm,
                long target,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            requireLongLessThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireLongLessThanExclusive(
                String targetTerm,
                long target,
                String limitTerm,
                long limit,
                VerifierThrown<Long, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireFloatMoreThanInclusive(
                String targetTerm,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatMoreThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireFloatMoreThanInclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit
        ) throws DefaultExceptionType {
            requireFloatMoreThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
                String targetTerm,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            requireFloatMoreThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireFloatMoreThanExclusive(
                String targetTerm,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatMoreThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireFloatMoreThanExclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit
        ) throws DefaultExceptionType {
            requireFloatMoreThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
                String targetTerm,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            requireFloatMoreThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireFloatLessThanInclusive(
                String targetTerm,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatLessThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireFloatLessThanInclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit
        ) throws DefaultExceptionType {
            requireFloatLessThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatLessThanInclusive(
                String targetTerm,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            requireFloatLessThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireFloatLessThanInclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireFloatLessThanExclusive(
                String targetTerm,
                float target,
                float limit
        ) throws DefaultExceptionType {
            requireFloatLessThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireFloatLessThanExclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit
        ) throws DefaultExceptionType {
            requireFloatLessThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireFloatLessThanExclusive(
                String targetTerm,
                float target,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            requireFloatLessThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireFloatLessThanExclusive(
                String targetTerm,
                float target,
                String limitTerm,
                float limit,
                VerifierThrown<Float, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireDoubleMoreThanInclusive(
                String targetTerm,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleMoreThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireDoubleMoreThanInclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleMoreThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
                String targetTerm,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            requireDoubleMoreThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target < limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireDoubleMoreThanExclusive(
                String targetTerm,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleMoreThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireDoubleMoreThanExclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleMoreThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
                String targetTerm,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            requireDoubleMoreThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target <= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireDoubleLessThanInclusive(
                String targetTerm,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleLessThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireDoubleLessThanInclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleLessThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
                String targetTerm,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            requireDoubleLessThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target > limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireDoubleLessThanExclusive(
                String targetTerm,
                double target,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleLessThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireDoubleLessThanExclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit
        ) throws DefaultExceptionType {
            requireDoubleLessThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
                String targetTerm,
                double target,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            requireDoubleLessThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
                String targetTerm,
                double target,
                String limitTerm,
                double limit,
                VerifierThrown<Double, ExceptionType> thrown
        ) throws ExceptionType {
            if (target >= limit) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireBigDecimalMoreThanInclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalMoreThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireBigDecimalMoreThanInclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalMoreThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            requireBigDecimalMoreThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) < 0) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireBigDecimalMoreThanExclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalMoreThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireBigDecimalMoreThanExclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalMoreThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            requireBigDecimalMoreThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) <= 0) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireBigDecimalLessThanInclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalLessThanInclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireBigDecimalLessThanInclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalLessThanInclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            requireBigDecimalLessThanInclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) > 0) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }

        default void requireBigDecimalLessThanExclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalLessThanExclusive(targetTerm, target, "", limit, defaultThrown());
        }

        default void requireBigDecimalLessThanExclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit
        ) throws DefaultExceptionType {
            requireBigDecimalLessThanExclusive(targetTerm, target, limitTerm, limit, defaultThrown());
        }

        default <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
                String targetTerm,
                BigDecimal target,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            requireBigDecimalLessThanExclusive(targetTerm, target, "", limit, thrown);
        }

        default <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
                String targetTerm,
                BigDecimal target,
                String limitTerm,
                BigDecimal limit,
                VerifierThrown<BigDecimal, ExceptionType> thrown
        ) throws ExceptionType {
            if (target.compareTo(limit) >= 0) {
                thrown.thrown(targetTerm, target, String.format(ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit));
            }
        }
    }

    public static class ErrorMsgFormat {

        public static final String REFUSE_NULL = "\"%s\" can't be null";

        public static final String REFUSE_NULL_OR_EMPTY = "\"%s\" can't be null or empty";

        public static final String MORE_THAN_INCLUSIVE = "%s(%s) must be more than or equal to %s(%s)";

        public static final String MORE_THAN_EXCLUSIVE = "%s(%s) must be more than %s(%s)";

        public static final String LESS_THAN_INCLUSIVE = "%s(%s) must be less than or equal to %s(%s)";

        public static final String LESS_THAN_EXCLUSIVE = "%s(%s) must be less than %s(%s)";

        public static final String REQUIRE_RANGE_INCLUSIVE = "%s(%s) must be in range [%s, %s]";

        public static final String REQUIRE_RANGE_EXCLUSIVE = "%s(%s) must be in range (%s, %s)";

        public static final String REFUSE_RANGE_INCLUSIVE = "%s(%s) can't be in range [%s, %s]";

        public static final String REFUSE_RANGE_EXCLUSIVE = "%s(%s) can't be in range (%s, %s)";
    }

    public static final Logic<IllegalArgumentException> INSTANCE = new Logic<IllegalArgumentException>() {
        @Override
        public <TargetType> VerifierThrown<TargetType, IllegalArgumentException> defaultThrown() {
            return (term, arg, errorMsg) -> {
                throw new IllegalArgumentException(errorMsg);
            };
        }
    };

    public static void refuseNull(String term, Object arg) throws IllegalArgumentException {
        INSTANCE.refuseNull(term, arg);
    }

    public static <TargetType, ExceptionType extends Exception> void refuseNull(
            String term,
            TargetType arg,
            VerifierThrown<TargetType, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.refuseNull(term, arg, thrown);
    }

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
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws IllegalArgumentException {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireShortLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static IntRangeVerifier ofRange(int min, int max) {
        return new IntRangeVerifier(min, max);
    }

    public static void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws IllegalArgumentException {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireIntLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static LongRangeVerifier ofRange(long min, long max) {
        return new LongRangeVerifier(min, max);
    }

    public static void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws IllegalArgumentException {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireLongLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static FloatRangeVerifier ofRange(float min, float max) {
        return new FloatRangeVerifier(min, max);
    }

    public static void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws IllegalArgumentException {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireFloatLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static DoubleRangeVerifier ofRange(double min, double max) {
        return new DoubleRangeVerifier(min, max);
    }

    public static void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws IllegalArgumentException {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireDoubleLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static BigDecimalRangeVerifier ofRange(BigDecimal min, BigDecimal max) {
        return new BigDecimalRangeVerifier(min, max);
    }

    public static void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalMoreThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanInclusive(targetTerm, target, limitTerm, limit, thrown);
    }

    public static void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limit);
    }

    public static void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws IllegalArgumentException {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limitTerm, limit);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limit, thrown);
    }

    public static <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, ExceptionType> thrown
    ) throws ExceptionType {
        INSTANCE.requireBigDecimalLessThanExclusive(targetTerm, target, limitTerm, limit, thrown);
    }

}
