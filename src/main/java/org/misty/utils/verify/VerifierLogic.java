package org.misty.utils.verify;

import java.math.BigDecimal;
import java.util.Optional;

public interface VerifierLogic<DefaultExceptionType extends Exception> extends VerifierThrownFactory<DefaultExceptionType> {

    default void refuseNull(String term, Object arg) throws DefaultExceptionType {
        refuseNull(term, arg, getThrower());
    }

    default <TargetType, ExceptionType extends Exception> void refuseNull(
            String term,
            TargetType arg,
            VerifierThrown<TargetType, VerifierErrorMsg<TargetType>, ExceptionType> thrown
    ) throws ExceptionType {
        if (arg == null || (arg instanceof Optional && !((Optional) arg).isPresent())) {
            thrown.thrown(new VerifierErrorMsg<>(term, arg, String.format(Verifier.ErrorMsgFormat.REFUSE_NULL, term)));
        }
    }

    default void refuseNullOrEmpty(String term, Object arg) throws DefaultExceptionType {
        refuseNullOrEmpty(term, arg, getThrower());
    }

    default <TargetType, ExceptionType extends Exception> void refuseNullOrEmpty(
            String term,
            TargetType arg,
            VerifierThrown<TargetType, VerifierErrorMsg<TargetType>, ExceptionType> thrown
    ) throws ExceptionType {
        if (Checker.isNullOrEmpty(arg)) {
            thrown.thrown(new VerifierErrorMsg<>(term, arg, String.format(Verifier.ErrorMsgFormat.REFUSE_NULL_OR_EMPTY, term)));
        }
    }

    default ShortRangeVerifierBase<DefaultExceptionType> ofRange(short min, short max) throws DefaultExceptionType {
        return new ShortRangeVerifierBase<>(min, max, this);
    }

    default void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortMoreThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE));
        }
    }

    default void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortMoreThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE));
        }
    }

    default void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortLessThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE));
        }
    }

    default void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortLessThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE));
        }
    }

    default IntRangeVerifierBase<DefaultExceptionType> ofRange(int min, int max) throws DefaultExceptionType {
        return new IntRangeVerifierBase<>(min, max, this);
    }

    default void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntMoreThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE));
        }
    }

    default void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntMoreThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE));
        }
    }

    default void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntLessThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE));
        }
    }

    default void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntLessThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE));
        }
    }

    default LongRangeVerifierBase<DefaultExceptionType> ofRange(long min, long max) throws DefaultExceptionType {
        return new LongRangeVerifierBase<>(min, max, this);
    }

    default void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongMoreThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE));
        }
    }

    default void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongMoreThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE));
        }
    }

    default void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongLessThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE));
        }
    }

    default void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongLessThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE));
        }
    }

    default FloatRangeVerifierBase<DefaultExceptionType> ofRange(float min, float max) throws DefaultExceptionType {
        return new FloatRangeVerifierBase<>(min, max, this);
    }

    default void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatMoreThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE));
        }
    }

    default void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatMoreThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE));
        }
    }

    default void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatLessThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE));
        }
    }

    default void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatLessThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE));
        }
    }

    default DoubleRangeVerifierBase<DefaultExceptionType> ofRange(double min, double max) throws DefaultExceptionType {
        return new DoubleRangeVerifierBase<>(min, max, this);
    }

    default void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleMoreThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE));
        }
    }

    default void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleMoreThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE));
        }
    }

    default void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleLessThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE));
        }
    }

    default void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleLessThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE));
        }
    }

    default BigDecimalRangeVerifierBase<DefaultExceptionType> ofRange(BigDecimal min, BigDecimal max) throws DefaultExceptionType {
        return new BigDecimalRangeVerifierBase<>(min, max, this);
    }

    default void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalMoreThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) < 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE));
        }
    }

    default void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalMoreThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) <= 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE));
        }
    }

    default void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalLessThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) > 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE));
        }
    }

    default void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalLessThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) >= 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit, Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE));
        }
    }

}
