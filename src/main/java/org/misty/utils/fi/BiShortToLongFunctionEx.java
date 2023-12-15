package org.misty.utils.fi;

public interface BiShortToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(short arg1, short arg2) throws Exception;

    default long execute(short arg1, short arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
