package org.misty.utils.verify;

public class RangeVerifierBase<DefaultExceptionType extends Exception> {

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public RangeVerifierBase(VerifierThrownFactory<DefaultExceptionType> thrownFactory) {
        this.thrownFactory = thrownFactory;
    }

    public VerifierThrownFactory<DefaultExceptionType> getThrownFactory() {
        return thrownFactory;
    }

}
