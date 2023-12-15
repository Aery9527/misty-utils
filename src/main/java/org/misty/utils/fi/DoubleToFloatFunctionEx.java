package org.misty.utils.fi;

public interface DoubleToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(double arg) throws Exception;

    default float execute(double arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
