package org.misty.utils.fi;

public interface BiShortToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(short arg1, short arg2) throws Exception;

    default int execute(short arg1, short arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
