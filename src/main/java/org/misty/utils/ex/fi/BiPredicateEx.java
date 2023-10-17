package org.misty.utils.ex.fi;

public interface BiPredicateEx<ArgType1, ArgType2> extends FunctionalInterfaceEx {

    boolean handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default boolean execute(ArgType1 arg1, ArgType2 arg2) {
        return FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
