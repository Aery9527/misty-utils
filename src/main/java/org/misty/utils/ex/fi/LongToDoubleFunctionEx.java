package org.misty.utils.ex.fi;

public interface LongToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(long arg) throws Exception;

    default double execute(long arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
