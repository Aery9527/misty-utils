package org.misty.utils.fi;

public interface BiFloatToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(float arg1, float arg2) throws Exception;

    default long execute(float arg1, float arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
