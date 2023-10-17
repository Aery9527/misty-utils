package org.misty.utils.ex.fi;

public interface ToLongFunctionEx<ArgType> extends FunctionalInterfaceEx {

    long handle(ArgType arg) throws Exception;

    default long execute(ArgType arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
