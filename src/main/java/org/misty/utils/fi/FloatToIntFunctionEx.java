package org.misty.utils.fi;

public interface FloatToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(float arg) throws Exception;

    default int execute(float arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
