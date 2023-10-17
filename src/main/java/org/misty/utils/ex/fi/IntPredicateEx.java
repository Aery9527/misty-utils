package org.misty.utils.ex.fi;

public interface IntPredicateEx extends FunctionalInterfaceEx {

    boolean handle(int arg) throws Exception;

    default boolean execute(int arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
