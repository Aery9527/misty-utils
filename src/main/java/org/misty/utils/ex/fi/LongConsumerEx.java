package org.misty.utils.ex.fi;

public interface LongConsumerEx extends FunctionalInterfaceEx {

    void handle(long arg) throws Exception;

    default void execute(long arg) {
        FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
