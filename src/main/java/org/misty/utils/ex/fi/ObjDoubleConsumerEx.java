package org.misty.utils.ex.fi;

public interface ObjDoubleConsumerEx<ArgType> extends FunctionalInterfaceEx {

    void handle(ArgType arg1, double arg2) throws Exception;

    default void execute(ArgType arg1, double arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
