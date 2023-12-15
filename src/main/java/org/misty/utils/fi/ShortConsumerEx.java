package org.misty.utils.fi;

public interface ShortConsumerEx extends FunctionalInterfaceEx {

    void handle(short arg) throws Exception;

    default void execute(short arg) {
        FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
