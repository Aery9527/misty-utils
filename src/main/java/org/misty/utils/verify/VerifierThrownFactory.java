package org.misty.utils.verify;

public interface VerifierThrownFactory<DefaultExceptionType extends Exception> {

    <TargetType, MagType extends VerifierErrorMsg<TargetType>> VerifierThrown<TargetType, MagType, DefaultExceptionType> getThrower();

}
