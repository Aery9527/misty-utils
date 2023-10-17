package org.misty.utils.ex.fi;

public interface DoubleToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(double arg) throws Exception;

    default long execute(double arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
