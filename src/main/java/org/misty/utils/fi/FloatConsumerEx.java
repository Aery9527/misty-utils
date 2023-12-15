package org.misty.utils.fi;

public interface FloatConsumerEx extends FunctionalInterfaceEx {

    void handle(float arg) throws Exception;

    default void execute(float arg) {
        FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
