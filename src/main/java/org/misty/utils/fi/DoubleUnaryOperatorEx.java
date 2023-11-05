package org.misty.utils.fi;

public interface DoubleUnaryOperatorEx extends FunctionalInterfaceEx {

    double handle(double arg) throws Exception;

    default double execute(double arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
