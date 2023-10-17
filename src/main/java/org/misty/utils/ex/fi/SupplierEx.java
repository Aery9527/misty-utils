package org.misty.utils.ex.fi;

public interface SupplierEx<ReturnType> extends FunctionalInterfaceEx {

    ReturnType handle() throws Exception;

    default ReturnType execute() {
        return FunctionalInterfaceEx.wrap(this);
    }

}
