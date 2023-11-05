package org.misty.utils.fi;

public interface ObjIntConsumerEx<ArgType> extends FunctionalInterfaceEx {

    void handle(ArgType arg1, int arg2) throws Exception;

    default void execute(ArgType arg1, int arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
