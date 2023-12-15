package org.misty.utils.fi;

public interface DoubleToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(double arg) throws Exception;

    default short execute(double arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
