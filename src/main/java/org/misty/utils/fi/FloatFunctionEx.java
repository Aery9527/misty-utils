package org.misty.utils.fi;

public interface FloatFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(float arg) throws Exception;

    default ReturnType execute(float arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
