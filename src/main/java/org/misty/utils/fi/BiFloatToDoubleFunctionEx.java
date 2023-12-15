package org.misty.utils.fi;

public interface BiFloatToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(float arg1, float arg2) throws Exception;

    default double execute(float arg1, float arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
