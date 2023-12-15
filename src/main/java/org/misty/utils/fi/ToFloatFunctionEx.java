package org.misty.utils.fi;

public interface ToFloatFunctionEx<ArgType> extends FunctionalInterfaceEx {

    float handle(ArgType arg) throws Exception;

    default float execute(ArgType arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}

