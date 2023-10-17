package org.misty.utils.ex.fi;

@FunctionalInterface
public interface ConsumerEx<ArgType> extends FunctionalInterfaceEx {

    void handle(ArgType arg) throws Exception;

    default void execute(ArgType arg) {
        FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
