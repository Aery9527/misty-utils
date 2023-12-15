package org.misty.utils.fi;

public interface BiLongToFloatFunctionEx extends FunctionalInterfaceEx {

    float handle(long arg1, long arg2) throws Exception;

    default float execute(long arg1, long arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
