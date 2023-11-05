package org.misty.utils.fi;

public interface DoubleBinaryOperatorEx extends FunctionalInterfaceEx {

    double handle(double arg1, double arg2) throws Exception;

    default double execute(double arg1, double arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
