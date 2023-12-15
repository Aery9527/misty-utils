package org.misty.utils.fi;

public interface ShortToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(short arg) throws Exception;

    default float execute(short arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
