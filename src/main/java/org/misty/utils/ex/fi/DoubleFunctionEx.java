package org.misty.utils.ex.fi;

public interface DoubleFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(double arg) throws Exception;

    default ReturnType execute(double arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
