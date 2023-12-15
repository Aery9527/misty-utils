package org.misty.utils.fi;

public interface BiLongToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(long arg1, long arg2) throws Exception;

    default short execute(long arg1, long arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
