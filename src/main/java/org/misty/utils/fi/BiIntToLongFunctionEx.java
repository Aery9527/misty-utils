package org.misty.utils.fi;

public interface BiIntToLongFunctionEx extends FunctionalInterfaceEx {

    long handle(int arg1, int arg2) throws Exception;

    default long execute(int arg1, int arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
