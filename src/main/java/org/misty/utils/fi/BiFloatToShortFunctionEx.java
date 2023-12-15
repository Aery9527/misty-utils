package org.misty.utils.fi;

public interface BiFloatToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(float arg1, float arg2) throws Exception;

    default short execute(float arg1, float arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
