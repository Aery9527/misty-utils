package org.misty.utils.ex.fi;

public interface ObjLongConsumerEx<ArgType> extends FunctionalInterfaceEx {

    void handle(ArgType arg1, long arg2) throws Exception;

    default void execute(ArgType arg1, long arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
