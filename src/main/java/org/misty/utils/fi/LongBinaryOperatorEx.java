package org.misty.utils.fi;

public interface LongBinaryOperatorEx extends FunctionalInterfaceEx {

    long handle(long arg1, long arg2) throws Exception;

    default long execute(long arg1, long arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
