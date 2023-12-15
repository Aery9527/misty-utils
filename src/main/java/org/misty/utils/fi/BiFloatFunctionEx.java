package org.misty.utils.fi;

public interface BiFloatFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(float arg1, float arg2) throws Exception;

    default ReturnType execute(float arg1, float arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
