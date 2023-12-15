package org.misty.utils.fi;

public interface ShortToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(short arg) throws Exception;

    default int execute(short arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
