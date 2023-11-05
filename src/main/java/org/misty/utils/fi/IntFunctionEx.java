package org.misty.utils.fi;

public interface IntFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(int arg) throws Exception;

    default ReturnType execute(int arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
