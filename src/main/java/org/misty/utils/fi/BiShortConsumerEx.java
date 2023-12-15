package org.misty.utils.fi;

public interface BiShortConsumerEx extends FunctionalInterfaceEx {

    void handle(short arg1, short arg2) throws Exception;

    default void execute(short arg1, short arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
