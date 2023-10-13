package org.misty.utils.ex;

@FunctionalInterface
public interface RunnableEx<ExceptionType extends Exception> extends FunctionalInterfaceEx {

    void handle() throws Exception;

    default void execute() throws ExceptionType {
        FunctionalInterfaceEx.wrap(this);
    }

}
