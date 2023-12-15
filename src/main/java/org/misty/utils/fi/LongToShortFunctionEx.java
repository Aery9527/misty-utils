package org.misty.utils.fi;

public interface LongToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(long arg) throws Exception;

    default short execute(long arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
