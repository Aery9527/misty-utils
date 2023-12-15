package org.misty.utils.fi;

public interface BiFloatToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(float arg1, float arg2) throws Exception;

    default int execute(float arg1, float arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
