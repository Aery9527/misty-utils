package org.misty.utils.fi;

public interface BiDoubleConsumerEx extends FunctionalInterfaceEx {

    void handle(double arg1, double arg2) throws Exception;

    default void execute(double arg1, double arg2) {
        FunctionalInterfaceEx.wrap(() -> handle(arg1, arg2));
    }

}
