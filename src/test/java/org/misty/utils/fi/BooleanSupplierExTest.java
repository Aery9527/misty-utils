package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BooleanSupplierExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        boolean forTestReturn = (int) (Math.random() * 2) == 0;
        BooleanSupplierEx supplierEx = () -> forTestReturn;
        Assertions.assertThat(supplierEx.execute()).isEqualTo(forTestReturn);

        BooleanSupplierEx supplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx1::execute).isInstanceOf(IOException.class);

        BooleanSupplierEx supplierEx2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx2::execute).isInstanceOf(InterruptedException.class);
    }

}
