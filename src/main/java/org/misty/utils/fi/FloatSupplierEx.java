package org.misty.utils.fi;

public interface FloatSupplierEx extends FunctionalInterfaceEx {

    float handle() throws Exception;

    default float execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
