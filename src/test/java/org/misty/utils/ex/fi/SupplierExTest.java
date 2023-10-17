package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

class SupplierExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        String forTestReturn = UUID.randomUUID().toString();
        SupplierEx<String> supplierEx = () -> forTestReturn;
        Assertions.assertThat(supplierEx.execute()).isEqualTo(forTestReturn);

        SupplierEx<String> supplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx1::execute).isInstanceOf(IOException.class);

        SupplierEx<String> supplierEx2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx2::execute).isInstanceOf(InterruptedException.class);
    }

}
