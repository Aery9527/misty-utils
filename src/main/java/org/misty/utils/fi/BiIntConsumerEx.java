package org.misty.utils.fi;

public interface BiIntConsumerEx extends FunctionalInterfaceEx {

    void handle(int arg1, int arg2) throws Exception;

    default void execute(int arg1, int arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
