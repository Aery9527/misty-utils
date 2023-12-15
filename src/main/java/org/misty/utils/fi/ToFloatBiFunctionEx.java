package org.misty.utils.fi;

public interface ToFloatBiFunctionEx<ArgType1, ArgType2> extends FunctionalInterfaceEx {

    float handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default float execute(ArgType1 arg1, ArgType2 arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
