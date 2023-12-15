package org.misty.utils.fi;

public interface BiFloatPredicateEx extends FunctionalInterfaceEx {

    boolean handle(float arg1, float arg2) throws Exception;

    default boolean execute(float arg1, float arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
