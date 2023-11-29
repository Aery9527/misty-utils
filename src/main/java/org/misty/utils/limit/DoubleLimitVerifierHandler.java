package org.misty.utils.limit;

public class DoubleLimitVerifierHandler implements LimitVerifier {

    private interface UnlimitedVerifier {
        void apply(double target, String operateTerm, double operate) throws ArithmeticException;
    }

    private final String targetTerm;

    private final LimiterThrown limiterThrown;

    private final DoubleLimitVerifier.SetVerifier setVerifier;

    private final DoubleLimitVerifier.OperationVerifier plusVerifier;

    private final DoubleLimitVerifier.OperationVerifier minusVerifier;

    private final UnlimitedVerifier unlimitedVerifier;

    public DoubleLimitVerifierHandler(String targetTerm,
                                      LimiterThrown limiterThrown,
                                      DoubleLimitVerifier limitVerifier,
                                      boolean acceptUnlimited) {
        this(targetTerm, limiterThrown, limitVerifier::verifySet, limitVerifier::verifyPlus, limitVerifier::verifyMinus, acceptUnlimited);
    }

    public DoubleLimitVerifierHandler(String targetTerm,
                                      LimiterThrown limiterThrown,
                                      DoubleLimitVerifier.SetVerifier setVerifier,
                                      DoubleLimitVerifier.OperationVerifier plusVerifier,
                                      DoubleLimitVerifier.OperationVerifier minusVerifier,
                                      boolean acceptUnlimited) {
        this.targetTerm = targetTerm;
        this.limiterThrown = limiterThrown;
        this.setVerifier = setVerifier;
        this.plusVerifier = plusVerifier;
        this.minusVerifier = minusVerifier;
        this.unlimitedVerifier = acceptUnlimited ? (target, term, operate) -> {
        } : (target, term, operate) -> {
            boolean notAllow = isInfiniteOrNaN(target) || isInfiniteOrNaN(operate);
            if (notAllow) {
                String msg = String.format(Limiter.ErrorMsgFormat.INFINITE_NAN_OPERATE, target, term, operate);
                this.limiterThrown.thrown(msg); // here support throw exception
                throw new UnsupportedOperationException(msg);
            }
        };
    }

    public void verifySet(double target) {
        if (isInfiniteOrNaN(target)) {
            String msg = String.format(Limiter.ErrorMsgFormat.INFINITE_NAN, target, "");
            this.limiterThrown.thrown(msg); // here support throw exception
            throw new UnsupportedOperationException(msg);
        }
        this.setVerifier.verify(target);
    }

    public double verifyPlus(double target, double plus) {
        this.unlimitedVerifier.apply(target, "plus", plus);
        double result = target + plus;
        this.plusVerifier.verify(target, plus, result);
        return result;
    }

    public double verifyMinus(double target, double minus) {
        this.unlimitedVerifier.apply(target, "minus", minus);
        double result = target - minus;
        this.minusVerifier.verify(target, minus, result);
        return result;
    }

    public boolean isInfiniteOrNaN(double value) {
        return Double.isInfinite(value) || Double.isNaN(value);
    }

}
