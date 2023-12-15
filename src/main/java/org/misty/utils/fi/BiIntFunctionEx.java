package org.misty.utils.fi;

public interface BiIntFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(int arg1, int arg2) throws Exception;

    default ReturnType execute(int arg1, int arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
