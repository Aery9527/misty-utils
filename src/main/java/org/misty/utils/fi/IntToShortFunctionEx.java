package org.misty.utils.fi;

public interface IntToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(int arg) throws Exception;

    default short execute(int arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
