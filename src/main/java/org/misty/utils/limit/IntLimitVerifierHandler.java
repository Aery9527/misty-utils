package org.misty.utils.limit;

import java.util.function.IntBinaryOperator;

public class IntLimitVerifierHandler implements LimitVerifier {

    private final String targetTerm;

    private final LimiterThrown limiterThrown;

    private final IntLimitVerifier.SetVerifier setVerifier;

    private final IntLimitVerifier.OperationVerifier plusVerifier;

    private final IntLimitVerifier.OperationVerifier minusVerifier;

    public IntLimitVerifierHandler(String targetTerm,
                                   LimiterThrown limiterThrown,
                                   IntLimitVerifier limitVerifier) {
        this(targetTerm, limiterThrown, limitVerifier::verifySet, limitVerifier::verifyPlus, limitVerifier::verifyMinus);
    }

    public IntLimitVerifierHandler(String targetTerm,
                                   LimiterThrown limiterThrown,
                                   IntLimitVerifier.SetVerifier setVerifier,
                                   IntLimitVerifier.OperationVerifier plusVerifier,
                                   IntLimitVerifier.OperationVerifier minusVerifier) {
        this.targetTerm = targetTerm;
        this.limiterThrown = limiterThrown;
        this.setVerifier = setVerifier;
        this.plusVerifier = plusVerifier;
        this.minusVerifier = minusVerifier;
    }

    public void verifySet(int target) {
        this.setVerifier.verify(target);
    }

    public int verifyPlus(int target, int plus) {
        int result = verifyOverflow(target, plus, "+", Math::addExact);
        this.plusVerifier.verify(target, plus, result);
        return result;
    }

    public int verifyMinus(int target, int minus) {
        int result = verifyOverflow(target, minus, "-", Math::subtractExact);
        this.minusVerifier.verify(target, minus, result);
        return result;
    }

    private int verifyOverflow(int target, int operate, String operator, IntBinaryOperator check) {
        try {
            return check.applyAsInt(target, operate);
        } catch (ArithmeticException e) {
            String overflowInfo = target >= 0 ? Limiter.ErrorMsgFormat.OVERFLOW_INTEGER_MAX : Limiter.ErrorMsgFormat.OVERFLOW_INTEGER_MIN;
            int result = "+".equals(operator) ? target + operate : target - operate;
            String msg = String.format(Limiter.ErrorMsgFormat.OPERATION, this.targetTerm, target, operator, operate, result + overflowInfo);
            this.limiterThrown.thrown(msg); // here support throw exception
            throw new UnsupportedOperationException(msg);
        }
    }

}
