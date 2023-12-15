package org.misty.utils.fi;

public interface ShortUnaryOperatorEx extends FunctionalInterfaceEx {

    short handle(short arg) throws Exception;

    default short execute(short arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}

