package org.misty.utils.fi;

@FunctionalInterface
public interface RunnableEx extends FunctionalInterfaceEx {

    void handle() throws Exception;

    default void execute() {
        FunctionalInterfaceEx.wrap(this);
    }

}
