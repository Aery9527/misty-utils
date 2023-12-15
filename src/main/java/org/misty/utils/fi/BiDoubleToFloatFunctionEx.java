package org.misty.utils.fi;

public interface BiDoubleToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(double arg1, double arg2) throws Exception;

    default float execute(double arg1, double arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
