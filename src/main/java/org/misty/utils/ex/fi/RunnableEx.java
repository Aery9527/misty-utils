package org.misty.utils.ex.fi;

@FunctionalInterface
public interface RunnableEx extends FunctionalInterfaceEx {

    void handle() throws Exception;

    default void execute() {
        FunctionalInterfaceEx.wrap(this);
    }

}
