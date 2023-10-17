package org.misty.utils.ex.fi;

public interface IntToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(int arg) throws Exception;

    default long execute(int arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
