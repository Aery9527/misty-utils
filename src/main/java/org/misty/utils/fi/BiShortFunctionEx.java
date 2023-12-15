package org.misty.utils.fi;

public interface BiShortFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(short arg1, short arg2) throws Exception;

    default ReturnType execute(short arg1, short arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
