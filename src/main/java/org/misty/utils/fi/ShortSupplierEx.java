package org.misty.utils.fi;

public interface ShortSupplierEx extends FunctionalInterfaceEx {

    short handle() throws Exception;

    default short execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
