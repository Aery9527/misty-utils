package org.misty.utils.fi;

public interface LongPredicateEx extends FunctionalInterfaceEx {

    boolean handle(long arg) throws Exception;

    default boolean execute(long arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
