package org.misty.utils.limit;

import java.util.function.BinaryOperator;

public class ShortLimitVerifierHandler implements LimitVerifier {

    private final String targetTerm;

    private final LimiterThrown limiterThrown;

    private final ShortLimitVerifier.SetVerifier setVerifier;

    private final ShortLimitVerifier.OperationVerifier plusVerifier;

    private final ShortLimitVerifier.OperationVerifier minusVerifier;

    public ShortLimitVerifierHandler(String targetTerm,
                                     LimiterThrown limiterThrown,
                                     ShortLimitVerifier limitVerifier) {
        this(targetTerm, limiterThrown, limitVerifier::verifySet, limitVerifier::verifyPlus, limitVerifier::verifyMinus);
    }

    public ShortLimitVerifierHandler(String targetTerm,
                                     LimiterThrown limiterThrown,
                                     ShortLimitVerifier.SetVerifier setVerifier,
                                     ShortLimitVerifier.OperationVerifier plusVerifier,
                                     ShortLimitVerifier.OperationVerifier minusVerifier) {
        this.targetTerm = targetTerm;
        this.limiterThrown = limiterThrown;
        this.setVerifier = setVerifier;
        this.plusVerifier = plusVerifier;
        this.minusVerifier = minusVerifier;
    }

    public void verifySet(short target) {
        this.setVerifier.verify(target);
    }

    public short verifyPlus(short target, short plus) {
        short result = verifyOverflow(target, plus, "+", (x, y) -> {
            int r = x + y;
            if (r < Short.MIN_VALUE || r > Short.MAX_VALUE) {
                throw new ArithmeticException("short overflow");
            }
            return (short) r;
        });
        this.plusVerifier.verify(target, plus, result);
        return result;
    }

    public short verifyMinus(short target, short minus) {
        short result = verifyOverflow(target, minus, "-", (x, y) -> {
            int r = x - y;
            if (r < Short.MIN_VALUE || r > Short.MAX_VALUE) {
                throw new ArithmeticException("short overflow");
            }
            return (short) r;
        });
        this.minusVerifier.verify(target, minus, result);
        return result;
    }

    private short verifyOverflow(short target, short operate, String operator, BinaryOperator<Short> check) {
        try {
            return check.apply(target, operate);
        } catch (ArithmeticException e) {
            String overflowInfo = target >= 0 ? Limiter.ErrorMsgFormat.OVERFLOW_SHORT_MAX : Limiter.ErrorMsgFormat.OVERFLOW_SHORT_MIN;
            short result = (short) ("+".equals(operator) ? (target + operate) : (target - operate));
            String msg = String.format(Limiter.ErrorMsgFormat.OPERATION, this.targetTerm, target, operator, operate, result + overflowInfo);
            this.limiterThrown.thrown(msg); // here support throw exception
            throw new UnsupportedOperationException(msg);
        }
    }

}
