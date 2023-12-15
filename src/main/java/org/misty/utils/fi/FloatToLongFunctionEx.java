package org.misty.utils.fi;

public interface FloatToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(float arg) throws Exception;

    default long execute(float arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
