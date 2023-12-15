package org.misty.utils.fi;

public interface BiDoubleFunctionEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle(double arg1, double arg2) throws Exception;

    default ReturnType execute(double arg1, double arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
