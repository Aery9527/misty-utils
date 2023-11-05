package org.misty.utils.fi;

public interface ToDoubleBiFunctionEx<ArgType1, ArgType2> extends FunctionalInterfaceEx {

    double handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default double execute(ArgType1 arg1, ArgType2 arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
