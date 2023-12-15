package org.misty.utils.fi;

public interface FloatToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(float arg) throws Exception;

    default short execute(float arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
