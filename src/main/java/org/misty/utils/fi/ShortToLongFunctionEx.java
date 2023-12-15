package org.misty.utils.fi;

public interface ShortToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(short arg) throws Exception;

    default long execute(short arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
