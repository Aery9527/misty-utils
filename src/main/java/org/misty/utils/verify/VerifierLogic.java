package org.misty.utils.verify;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public interface VerifierLogic<DefaultExceptionType extends Exception> extends VerifierThrownFactory<DefaultExceptionType> {

    static String wrapTitle(String title) {
        return Checker.isNullOrEmpty(title) ? "" : "<" + title + "> ";
    }

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
        return ofRange("", min, max);
    }

    default ShortRangeVerifierBase<DefaultExceptionType> ofRange(String title, short min, short max) throws DefaultExceptionType {
        return new ShortRangeVerifierBase<>(title, min, max, this);
    }

    default void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireShortMoreThanInclusive(
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireShortMoreThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortMoreThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortMoreThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortMoreThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireShortMoreThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortMoreThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortMoreThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortMoreThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortMoreThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireShortLessThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortLessThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortLessThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortLessThanInclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            short target,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireShortLessThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit
    ) throws DefaultExceptionType {
        requireShortLessThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            short target,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortLessThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        requireShortLessThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireShortLessThanExclusive(
            String title,
            String targetTerm,
            short target,
            String limitTerm,
            short limit,
            VerifierThrown<Short, VerifierLimitErrorMsg<Short>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default IntRangeVerifierBase<DefaultExceptionType> ofRange(int min, int max) throws DefaultExceptionType {
        return ofRange("", min, max);
    }

    default IntRangeVerifierBase<DefaultExceptionType> ofRange(String title, int min, int max) throws DefaultExceptionType {
        return new IntRangeVerifierBase<>(title, min, max, this);
    }

    default void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireIntMoreThanInclusive(
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireIntMoreThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntMoreThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntMoreThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntMoreThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireIntMoreThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntMoreThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntMoreThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntMoreThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntMoreThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireIntLessThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntLessThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntLessThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntLessThanInclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            int target,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireIntLessThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit
    ) throws DefaultExceptionType {
        requireIntLessThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            int target,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntLessThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        requireIntLessThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireIntLessThanExclusive(
            String title,
            String targetTerm,
            int target,
            String limitTerm,
            int limit,
            VerifierThrown<Integer, VerifierLimitErrorMsg<Integer>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default LongRangeVerifierBase<DefaultExceptionType> ofRange(long min, long max) throws DefaultExceptionType {
        return ofRange("", min, max);
    }

    default LongRangeVerifierBase<DefaultExceptionType> ofRange(String title, long min, long max) throws DefaultExceptionType {
        return new LongRangeVerifierBase<>(title, min, max, this);
    }

    default void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireLongMoreThanInclusive(
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireLongMoreThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongMoreThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongMoreThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongMoreThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireLongMoreThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongMoreThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongMoreThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongMoreThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongMoreThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireLongLessThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongLessThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongLessThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongLessThanInclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            long target,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireLongLessThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit
    ) throws DefaultExceptionType {
        requireLongLessThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            long target,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongLessThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        requireLongLessThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireLongLessThanExclusive(
            String title,
            String targetTerm,
            long target,
            String limitTerm,
            long limit,
            VerifierThrown<Long, VerifierLimitErrorMsg<Long>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default FloatRangeVerifierBase<DefaultExceptionType> ofRange(float min, float max) throws DefaultExceptionType {
        return ofRange("", min, max);
    }

    default FloatRangeVerifierBase<DefaultExceptionType> ofRange(String title, float min, float max) throws DefaultExceptionType {
        return new FloatRangeVerifierBase<>(title, min, max, this);

    }

    default void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatMoreThanInclusive(
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireFloatMoreThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatMoreThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatMoreThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireFloatMoreThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatMoreThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatMoreThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatMoreThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatMoreThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireFloatLessThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatLessThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatLessThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatLessThanInclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            float target,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireFloatLessThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit
    ) throws DefaultExceptionType {
        requireFloatLessThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            float target,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatLessThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        requireFloatLessThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireFloatLessThanExclusive(
            String title,
            String targetTerm,
            float target,
            String limitTerm,
            float limit,
            VerifierThrown<Float, VerifierLimitErrorMsg<Float>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default DoubleRangeVerifierBase<DefaultExceptionType> ofRange(double min, double max) throws DefaultExceptionType {
        return ofRange("", min, max);
    }

    default DoubleRangeVerifierBase<DefaultExceptionType> ofRange(String title, double min, double max) throws DefaultExceptionType {
        return new DoubleRangeVerifierBase<>(title, min, max, this);
    }

    default void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleMoreThanInclusive(
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireDoubleMoreThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleMoreThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleMoreThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireDoubleMoreThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleMoreThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleMoreThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleMoreThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleMoreThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireDoubleLessThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleLessThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleLessThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanInclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            double target,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireDoubleLessThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit
    ) throws DefaultExceptionType {
        requireDoubleLessThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            double target,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleLessThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        requireDoubleLessThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireDoubleLessThanExclusive(
            String title,
            String targetTerm,
            double target,
            String limitTerm,
            double limit,
            VerifierThrown<Double, VerifierLimitErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= limit) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default BigDecimalRangeVerifierBase<DefaultExceptionType> ofRange(BigDecimal min, BigDecimal max) throws DefaultExceptionType {
        return ofRange("", min, max);
    }

    default BigDecimalRangeVerifierBase<DefaultExceptionType> ofRange(String title, BigDecimal min, BigDecimal max) throws DefaultExceptionType {
        return new BigDecimalRangeVerifierBase<>(title, min, max, this);
    }

    default void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalMoreThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigDecimalMoreThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalMoreThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalMoreThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) < 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigDecimalMoreThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalMoreThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalMoreThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalMoreThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalMoreThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) <= 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigDecimalLessThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalLessThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalLessThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanInclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) > 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
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
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigDecimalLessThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit
    ) throws DefaultExceptionType {
        requireBigDecimalLessThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
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
            String title,
            String targetTerm,
            BigDecimal target,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalLessThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigDecimalLessThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigDecimalLessThanExclusive(
            String title,
            String targetTerm,
            BigDecimal target,
            String limitTerm,
            BigDecimal limit,
            VerifierThrown<BigDecimal, VerifierLimitErrorMsg<BigDecimal>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) >= 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default BigIntegerRangeVerifierBase<DefaultExceptionType> ofRange(BigInteger min, BigInteger max) throws DefaultExceptionType {
        return ofRange("", min, max);
    }

    default BigIntegerRangeVerifierBase<DefaultExceptionType> ofRange(String title, BigInteger min, BigInteger max) throws DefaultExceptionType {
        return new BigIntegerRangeVerifierBase<>(title, min, max, this);
    }

    default void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerMoreThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerMoreThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerMoreThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) < 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerMoreThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerMoreThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerMoreThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerMoreThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerMoreThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) <= 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanInclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanInclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanInclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanInclusive(title, targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerLessThanInclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerLessThanInclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerLessThanInclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanInclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) > 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

    default void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanExclusive(targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanExclusive(title, targetTerm, target, "", limit, getThrower());
    }

    default void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanExclusive(targetTerm, target, limitTerm, limit, getThrower());
    }

    default void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit
    ) throws DefaultExceptionType {
        requireBigIntegerLessThanExclusive(title, targetTerm, target, limitTerm, limit, getThrower());
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerLessThanExclusive(targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerLessThanExclusive(title, targetTerm, target, "", limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        requireBigIntegerLessThanExclusive("", targetTerm, target, limitTerm, limit, thrown);
    }

    default <ExceptionType extends Exception> void requireBigIntegerLessThanExclusive(
            String title,
            String targetTerm,
            BigInteger target,
            String limitTerm,
            BigInteger limit,
            VerifierThrown<BigInteger, VerifierLimitErrorMsg<BigInteger>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target.compareTo(limit) >= 0) {
            thrown.thrown(new VerifierLimitErrorMsg<>(targetTerm, target, limitTerm, limit,
                    wrapTitle(title) + String.format(Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE, targetTerm, target, limitTerm, limit)));
        }
    }

}
