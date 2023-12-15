package org.misty.utils.fi;

public interface BiLongFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(long arg1, long arg2) throws Exception;

    default ReturnType execute(long arg1, long arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
