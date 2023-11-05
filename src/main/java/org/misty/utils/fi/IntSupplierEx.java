package org.misty.utils.fi;

public interface IntSupplierEx extends FunctionalInterfaceEx {

    int handle() throws Exception;

    default int execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
