package org.misty.utils.fi;

public interface IntToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(int arg) throws Exception;

    default float execute(int arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
