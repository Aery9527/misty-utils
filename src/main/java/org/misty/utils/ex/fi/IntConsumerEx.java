package org.misty.utils.ex.fi;

public interface IntConsumerEx extends FunctionalInterfaceEx {

    void handle(int arg) throws Exception;

    default void execute(int arg) {
        FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
