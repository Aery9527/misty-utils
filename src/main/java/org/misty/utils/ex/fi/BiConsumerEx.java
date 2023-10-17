package org.misty.utils.ex.fi;

public interface BiConsumerEx<ArgType1, ArgType2> extends FunctionalInterfaceEx {

    void handle(ArgType1 arg1, ArgType2 arg2) throws Exception;

    default void execute(ArgType1 arg1, ArgType2 arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
