package org.misty.utils.fi;

public interface LongSupplierEx extends FunctionalInterfaceEx {

    long handle() throws Exception;

    default long execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
