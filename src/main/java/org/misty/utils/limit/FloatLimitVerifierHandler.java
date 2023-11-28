package org.misty.utils.limit;

public class FloatLimitVerifierHandler implements LimitVerifier {

    private interface UnlimitedVerifier {
        void apply(float target, String operateTerm, float operate) throws ArithmeticException;
    }

    private final String targetTerm;

    private final LimiterThrown limiterThrown;

    private final FloatLimitVerifier.SetVerifier setVerifier;

    private final FloatLimitVerifier.OperationVerifier plusVerifier;

    private final FloatLimitVerifier.OperationVerifier minusVerifier;

    private final UnlimitedVerifier unlimitedVerifier;

    public FloatLimitVerifierHandler(String targetTerm,
                                     LimiterThrown limiterThrown,
                                     FloatLimitVerifier limitVerifier,
                                     boolean acceptUnlimited) {
        this(targetTerm, limiterThrown, limitVerifier::verifySet, limitVerifier::verifyPlus, limitVerifier::verifyMinus, acceptUnlimited);
    }

    public FloatLimitVerifierHandler(String targetTerm,
                                     LimiterThrown limiterThrown,
                                     FloatLimitVerifier.SetVerifier setVerifier,
                                     FloatLimitVerifier.OperationVerifier plusVerifier,
                                     FloatLimitVerifier.OperationVerifier minusVerifier,
                                     boolean acceptUnlimited) {
        this.targetTerm = targetTerm;
        this.limiterThrown = limiterThrown;
        this.setVerifier = setVerifier;
        this.plusVerifier = plusVerifier;
        this.minusVerifier = minusVerifier;
        this.unlimitedVerifier = acceptUnlimited ? (target, term, operate) -> {
        } : (target, term, operate) -> {
            boolean notAllow = Float.isInfinite(target) || Float.isNaN(target);
            notAllow |= Float.isInfinite(operate) || Float.isNaN(operate);
            if (notAllow) {
                String msg = String.format(Limiter.ErrorMsgFormat.OVERFLOW_FLOAT, target, term, operate);
                this.limiterThrown.thrown(msg); // here support throw exception
                throw new UnsupportedOperationException(msg);
            }
        };
    }

    public void verifySet(float target) {
        this.setVerifier.verify(target);
    }

    public float verifyPlus(float target, float plus) {
        this.unlimitedVerifier.apply(target, "plus", plus);
        float result = target + plus;
        this.plusVerifier.verify(target, plus, result);
        return result;
    }

    public float verifyMinus(float target, float minus) {
        this.unlimitedVerifier.apply(target, "minus", minus);
        float result = target - minus;
        this.minusVerifier.verify(target, minus, result);
        return result;
    }

}
