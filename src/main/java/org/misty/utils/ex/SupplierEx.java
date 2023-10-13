package org.misty.utils.ex;

public interface SupplierEx<ReturnType, ExceptionType extends Exception> extends FunctionalInterfaceEx {

    ReturnType handle() throws Exception;

    default ReturnType execute() throws ExceptionType {
        return FunctionalInterfaceEx.wrap(this);
    }

}
