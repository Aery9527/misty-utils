package org.misty.utils.fi;

public interface IntBinaryOperatorEx extends FunctionalInterfaceEx {

    int handle(int arg1, int arg2) throws Exception;

    default int execute(int arg1, int arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
