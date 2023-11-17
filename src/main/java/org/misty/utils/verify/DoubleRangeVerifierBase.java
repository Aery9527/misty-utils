package org.misty.utils.verify;

public class DoubleRangeVerifierBase<DefaultExceptionType extends Exception> {

    private final double min;

    private final double max;

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public DoubleRangeVerifierBase(double min, double max, VerifierThrownFactory<DefaultExceptionType> thrownFactory) throws DefaultExceptionType {
        Verifier.requireDoubleLessThanInclusive("min", min, "max", max, thrownFactory.getThrower());
        this.min = min;
        this.max = max;
        this.thrownFactory = thrownFactory;
    }

    public void requireInclusive(String term, double target) throws DefaultExceptionType {
        requireInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireInclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target < this.min || target > this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE));
        }
    }

    public void requireExclusive(String term, double target) throws DefaultExceptionType {
        requireExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void requireExclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target <= this.min || target >= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE));
        }
    }

    public void refuseInclusive(String term, double target) throws DefaultExceptionType {
        refuseInclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseInclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target >= this.min && target <= this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE));
        }
    }

    public void refuseExclusive(String term, double target) throws DefaultExceptionType {
        refuseExclusive(term, target, this.thrownFactory.getThrower());
    }

    public <ExceptionType extends Exception> void refuseExclusive(
            String term,
            double target,
            VerifierThrown<Double, VerifierRangeErrorMsg<Double>, ExceptionType> thrown
    ) throws ExceptionType {
        if (target > this.min && target < this.max) {
            thrown.thrown(new VerifierRangeErrorMsg<>(term, target, this.min, this.max, Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE));
        }
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

}
