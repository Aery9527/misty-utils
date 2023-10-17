package org.misty.utils.ex.fi;

public interface DoubleConsumerEx extends FunctionalInterfaceEx {

    void handle(double arg) throws Exception;

    default void execute(double arg) {
        FunctionalInterfaceEx.wrap(() -> handle(arg));
    }

}
