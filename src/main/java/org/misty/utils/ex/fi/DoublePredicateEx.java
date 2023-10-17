package org.misty.utils.ex.fi;

public interface DoublePredicateEx extends FunctionalInterfaceEx {

    boolean handle(double arg) throws Exception;

    default boolean execute(double arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
