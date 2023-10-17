package org.misty.utils.ex.fi;

public interface PredicateEx<ArgType> extends FunctionalInterfaceEx {

    boolean handle(ArgType arg) throws Exception;

    default boolean execute(ArgType arg) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
