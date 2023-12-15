package org.misty.utils.fi;

public interface BiShortPredicateEx extends FunctionalInterfaceEx {

    boolean handle(short arg1, short arg2) throws Exception;

    default boolean execute(short arg1, short arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
