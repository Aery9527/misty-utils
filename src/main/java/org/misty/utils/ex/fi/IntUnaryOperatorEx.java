package org.misty.utils.ex.fi;

public interface IntUnaryOperatorEx extends FunctionalInterfaceEx {

    int handle(int arg) throws Exception;

    default int execute(int arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
