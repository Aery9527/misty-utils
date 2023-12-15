package org.misty.utils.fi;

public interface BiLongPredicateEx extends FunctionalInterfaceEx {

    boolean handle(long arg1, long arg2) throws Exception;

    default boolean execute(long arg1, long arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
