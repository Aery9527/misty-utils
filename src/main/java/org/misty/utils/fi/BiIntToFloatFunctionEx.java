package org.misty.utils.fi;

public interface BiIntToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(int arg1, int arg2) throws Exception;

    default float execute(int arg1, int arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
