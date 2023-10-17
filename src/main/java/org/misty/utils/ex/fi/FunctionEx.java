package org.misty.utils.ex.fi;

@FunctionalInterface
public interface FunctionEx<ArgType, ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(ArgType arg) throws Exception;

    default ReturnType execute(ArgType arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
