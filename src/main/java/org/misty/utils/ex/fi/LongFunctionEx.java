package org.misty.utils.ex.fi;

public interface LongFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(long arg) throws Exception;

    default ReturnType execute(long arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
