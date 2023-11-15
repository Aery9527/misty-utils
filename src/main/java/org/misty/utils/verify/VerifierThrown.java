package org.misty.utils.verify;

public interface VerifierThrown<TargetType, ExceptionType extends Exception> {

    void thrown(String term, TargetType arg, String errorMsg) throws ExceptionType;

}
