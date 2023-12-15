package org.misty.utils.fi;

public interface ShortToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(short arg) throws Exception;

    default double execute(short arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
