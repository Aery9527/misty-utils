package org.misty.utils.fi;

public interface ToShortFunctionEx<ArgType> extends FunctionalInterfaceEx {

    short handle(ArgType arg) throws Exception;

    default short execute(ArgType arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}

