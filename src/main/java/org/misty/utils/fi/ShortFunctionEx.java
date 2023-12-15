package org.misty.utils.fi;

public interface ShortFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(short arg) throws Exception;

    default ReturnType execute(short arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
