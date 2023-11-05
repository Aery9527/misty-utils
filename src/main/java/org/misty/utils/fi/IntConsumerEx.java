package org.misty.utils.fi;

public interface IntConsumerEx extends FunctionalInterfaceEx {

    void handle(int arg) throws Exception;

    default void execute(int arg) {
        FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
