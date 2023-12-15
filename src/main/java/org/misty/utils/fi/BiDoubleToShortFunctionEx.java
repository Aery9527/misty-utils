package org.misty.utils.fi;

public interface BiDoubleToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(double arg1, double arg2) throws Exception;

    default short execute(double arg1, double arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
