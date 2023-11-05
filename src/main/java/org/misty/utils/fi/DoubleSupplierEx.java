package org.misty.utils.fi;

public interface DoubleSupplierEx extends FunctionalInterfaceEx {

    double handle() throws Exception;

    default double execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
