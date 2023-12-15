package org.misty.utils.fi;

public interface LongToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(long arg) throws Exception;

    default float execute(long arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
