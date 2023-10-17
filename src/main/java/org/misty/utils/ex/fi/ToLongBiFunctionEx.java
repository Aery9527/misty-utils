package org.misty.utils.ex.fi;

public interface ToLongBiFunctionEx<ArgType1, ArgType2> extends FunctionalInterfaceEx {

    long handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default long execute(ArgType1 arg1, ArgType2 arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
