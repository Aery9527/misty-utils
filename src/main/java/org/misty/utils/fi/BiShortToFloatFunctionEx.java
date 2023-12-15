package org.misty.utils.fi;

public interface BiShortToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(short arg1, short arg2) throws Exception;

    default float execute(short arg1, short arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
