package org.misty.utils.limit;

import java.util.function.LongBinaryOperator;

public class LongLimitVerifierHandler implements LimitVerifier {

    private final String targetTerm;

    private final LimiterThrown limiterThrown;

    private final LongLimitVerifier.SetVerifier setVerifier;

    private final LongLimitVerifier.OperationVerifier plusVerifier;

    private final LongLimitVerifier.OperationVerifier minusVerifier;

    public LongLimitVerifierHandler(String targetTerm,
                                    LimiterThrown limiterThrown,
                                    LongLimitVerifier limitVerifier) {
        this(targetTerm, limiterThrown, limitVerifier::verifySet, limitVerifier::verifyPlus, limitVerifier::verifyMinus);
    }

    public LongLimitVerifierHandler(String targetTerm,
                                    LimiterThrown limiterThrown,
                                    LongLimitVerifier.SetVerifier setVerifier,
                                    LongLimitVerifier.OperationVerifier plusVerifier,
                                    LongLimitVerifier.OperationVerifier minusVerifier) {
        this.targetTerm = targetTerm;
        this.limiterThrown = limiterThrown;
        this.setVerifier = setVerifier;
        this.plusVerifier = plusVerifier;
        this.minusVerifier = minusVerifier;
    }

    public void verifySet(long target) {
        this.setVerifier.verify(target);
    }

    public long verifyPlus(long target, long plus) {
        long result = verifyOverflow(target, plus, "+", Math::addExact);
        this.plusVerifier.verify(target, plus, result);
        return result;
    }

    public long verifyMinus(long target, long minus) {
        long result = verifyOverflow(target, minus, "-", Math::subtractExact);
        this.minusVerifier.verify(target, minus, result);
        return result;
    }

    private long verifyOverflow(long target, long operate, String operator, LongBinaryOperator check) {
        try {
            return check.applyAsLong(target, operate);
        } catch (ArithmeticException e) {
            String overflowInfo = target >= 0 ? Limiter.ErrorMsgFormat.OVERFLOW_LONG_MAX : Limiter.ErrorMsgFormat.OVERFLOW_LONG_MIN;
            long result = "+".equals(operator) ? target + operate : target - operate;
            String msg = String.format(Limiter.ErrorMsgFormat.OPERATION, this.targetTerm, target, operator, operate, result + overflowInfo);
            this.limiterThrown.thrown(msg); // here support throw exception
            throw new UnsupportedOperationException(msg);
        }
    }

}
