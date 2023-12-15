package org.misty.utils.fi;

public interface ShortBinaryOperatorEx extends FunctionalInterfaceEx {

    short handle(short arg1, short arg2) throws Exception;

    default short execute(short arg1, short arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
