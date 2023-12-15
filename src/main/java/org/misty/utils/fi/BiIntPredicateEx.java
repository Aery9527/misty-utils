package org.misty.utils.fi;

public interface BiIntPredicateEx extends FunctionalInterfaceEx {

    boolean handle(int arg1, int arg2) throws Exception;

    default boolean execute(int arg1, int arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
