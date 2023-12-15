package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DoubleSupplierExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        double forTestReturn = Math.random();
        DoubleSupplierEx supplierEx = () -> forTestReturn;
        Assertions.assertThat(supplierEx.execute()).isEqualTo(forTestReturn);

        DoubleSupplierEx supplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx1::execute).isInstanceOf(IOException.class);

        DoubleSupplierEx supplierEx2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx2::execute).isInstanceOf(InterruptedException.class);
    }

}
