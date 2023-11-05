package org.misty.utils.fi;

public interface LongUnaryOperatorEx extends FunctionalInterfaceEx {

    long handle(long arg) throws Exception;

    default long execute(long arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
