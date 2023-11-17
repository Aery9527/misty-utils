package org.misty.utils.verify;

public interface VerifierThrown<TargetType, MagType extends VerifierErrorMsg<TargetType>, ExceptionType extends Exception> {

    void thrown(MagType msg) throws ExceptionType;

}
