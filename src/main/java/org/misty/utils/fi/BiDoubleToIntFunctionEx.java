package org.misty.utils.fi;

public interface BiDoubleToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(double arg1, double arg2) throws Exception;

    default int execute(double arg1, double arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
