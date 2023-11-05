package org.misty.utils.fi;

public interface LongToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(long arg) throws Exception;

    default int execute(long arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
