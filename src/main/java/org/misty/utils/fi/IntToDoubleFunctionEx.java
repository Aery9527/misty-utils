package org.misty.utils.fi;

public interface IntToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(int arg) throws Exception;

    default double execute(int arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
