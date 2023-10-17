package org.misty.utils.ex.fi;

public interface BiFunctionEx<ArgType1, ArgType2, ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default ReturnType execute(ArgType1 arg1, ArgType2 arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
