package org.misty.utils.fi;

public interface BiLongToIntFunctionEx extends FunctionalInterfaceEx {

    int handle(long arg1, long arg2) throws Exception;

    default int execute(long arg1, long arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
