package org.misty.utils.fi;

public interface BiDoublePredicateEx extends FunctionalInterfaceEx {

    boolean handle(double arg1, double arg2) throws Exception;

    default boolean execute(double arg1, double arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
