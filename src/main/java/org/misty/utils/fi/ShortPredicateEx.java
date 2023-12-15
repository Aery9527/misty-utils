package org.misty.utils.fi;

public interface ShortPredicateEx extends FunctionalInterfaceEx {

    boolean handle(short arg) throws Exception;

    default boolean execute(short arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
