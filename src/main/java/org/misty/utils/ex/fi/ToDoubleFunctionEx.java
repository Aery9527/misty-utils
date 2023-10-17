package org.misty.utils.ex.fi;

public interface ToDoubleFunctionEx<ArgType> extends FunctionalInterfaceEx {

    double handle(ArgType arg) throws Exception;

    default double execute(ArgType arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
