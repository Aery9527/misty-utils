package org.misty.utils.fi;

public interface BiFloatConsumerEx extends FunctionalInterfaceEx {

    void handle(float arg1, float arg2) throws Exception;

    default void execute(float arg1, float arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
