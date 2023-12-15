package org.misty.utils.fi;

public interface BiIntToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(int arg1, int arg2) throws Exception;

    default double execute(int arg1, int arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
