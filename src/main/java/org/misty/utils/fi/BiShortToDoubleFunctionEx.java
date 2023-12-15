package org.misty.utils.fi;

public interface BiShortToDoubleFunctionEx extends FunctionalInterfaceEx {

    double handle(short arg1, short arg2) throws Exception;

    default double execute(short arg1, short arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
