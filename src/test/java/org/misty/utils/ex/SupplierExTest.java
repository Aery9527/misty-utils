package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SupplierExTest {

    @Test
    void execute() {
        SupplierEx<Void, IOException> supplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx1::execute)
                .isInstanceOf(IOException.class);

        // When the declared exception type differs from the thrown exception type, no error occurs. It's quite magical.
        SupplierEx<Void, InterruptedException> supplierEx2 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx2::execute)
                .isInstanceOf(IOException.class);

        SupplierEx<Void, IOException> supplierEx3 = () -> FunctionalInterfaceExTest.thrown(new IOException()); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx3::execute)
                .isInstanceOf(IOException.class);

        SupplierEx<Void, IOException> supplierEx4 = () -> FunctionalInterfaceExTest.thrown(new InterruptedException()); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(supplierEx4::execute)
                .isInstanceOf(InterruptedException.class);
    }

}
