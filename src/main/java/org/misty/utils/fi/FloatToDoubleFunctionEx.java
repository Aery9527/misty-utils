package org.misty.utils.fi;

public interface FloatToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(float arg) throws Exception;

    default double execute(float arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
