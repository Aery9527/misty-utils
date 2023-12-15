package org.misty.utils.fi;

public interface BiDoubleToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(double arg1, double arg2) throws Exception;

    default long execute(double arg1, double arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
