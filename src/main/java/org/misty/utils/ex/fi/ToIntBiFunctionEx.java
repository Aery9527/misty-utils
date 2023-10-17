package org.misty.utils.ex.fi;

public interface ToIntBiFunctionEx<ArgType1, ArgType2> extends FunctionalInterfaceEx {

    int handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default int execute(ArgType1 arg1, ArgType2 arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
