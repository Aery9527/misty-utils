package org.misty.utils.fi;

public interface FloatPredicateEx extends FunctionalInterfaceEx {

    boolean handle(float arg) throws Exception;

    default boolean execute(float arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
