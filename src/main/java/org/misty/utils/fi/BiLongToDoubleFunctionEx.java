package org.misty.utils.fi;

public interface BiLongToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(long arg1, long arg2) throws Exception;

    default double execute(long arg1, long arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
