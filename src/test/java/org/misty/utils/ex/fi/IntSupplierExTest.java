package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class IntSupplierExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        int forTestReturn = (int) (Math.random() * 2);
        IntSupplierEx supplierEx = () -> forTestReturn;
        Assertions.assertThat(supplierEx.execute()).isEqualTo(forTestReturn);

        IntSupplierEx supplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx1::execute).isInstanceOf(IOException.class);

        IntSupplierEx supplierEx2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx2::execute).isInstanceOf(InterruptedException.class);
    }

}
