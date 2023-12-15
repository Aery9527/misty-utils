package org.misty.utils.fi;

public interface BiIntToShortFunctionEx extends FunctionalInterfaceEx {

    short handle(int arg1, int arg2) throws Exception;

    default short execute(int arg1, int arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
