package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class LongSupplierExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        long forTestReturn = (long) (Math.random() * 100);
        LongSupplierEx supplierEx = () -> forTestReturn;
        Assertions.assertThat(supplierEx.execute()).isEqualTo(forTestReturn);

        LongSupplierEx supplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx1::execute).isInstanceOf(IOException.class);

        LongSupplierEx supplierEx2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx2::execute).isInstanceOf(InterruptedException.class);
    }

}
