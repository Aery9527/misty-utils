package org.misty.utils.ex.fi;

public interface BooleanSupplierEx extends FunctionalInterfaceEx {

    boolean handle() throws Exception;

    default boolean execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
