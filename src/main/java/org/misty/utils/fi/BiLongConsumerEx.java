package org.misty.utils.fi;

public interface BiLongConsumerEx extends FunctionalInterfaceEx {

    void handle(long arg1, long arg2) throws Exception;

    default void execute(long arg1, long arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
