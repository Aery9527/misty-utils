package org.misty.utils.verify;

public interface VerifierThrownFactory<DefaultExceptionType extends Exception> {

    <TargetType> VerifierThrown<TargetType, DefaultExceptionType> getThrower();

}
