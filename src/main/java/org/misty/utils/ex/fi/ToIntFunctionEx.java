package org.misty.utils.ex.fi;

public interface ToIntFunctionEx<ArgType> extends FunctionalInterfaceEx {

    int handle(ArgType arg) throws Exception;

    default int execute(ArgType arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
