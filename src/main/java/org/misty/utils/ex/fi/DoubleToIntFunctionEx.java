package org.misty.utils.ex.fi;

public interface DoubleToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(double arg) throws Exception;

    default int execute(double arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
