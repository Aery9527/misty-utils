package org.misty.utils.fi;

public interface BooleanSupplierEx extends FunctionalInterfaceEx {

    boolean handle() throws Exception;

    default boolean execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
