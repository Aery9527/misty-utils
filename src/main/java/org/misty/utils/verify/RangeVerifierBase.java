package org.misty.utils.verify;

public class RangeVerifierBase<DefaultExceptionType extends Exception> {

    private final String title;

    private final VerifierThrownFactory<DefaultExceptionType> thrownFactory;

    public RangeVerifierBase(String title, VerifierThrownFactory<DefaultExceptionType> thrownFactory) {
        this.title = VerifierLogic.wrapTitle(title);
        this.thrownFactory = thrownFactory;
    }

    public VerifierThrownFactory<DefaultExceptionType> getThrownFactory() {
        return thrownFactory;
    }

    public String getTitle() {
        return title;
    }

}
