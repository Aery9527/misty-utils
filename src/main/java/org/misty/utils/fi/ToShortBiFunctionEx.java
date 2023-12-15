package org.misty.utils.fi;

public interface ToShortBiFunctionEx<ArgType1, ArgType2> extends FunctionalInterfaceEx {

    short handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default short execute(ArgType1 arg1, ArgType2 arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
