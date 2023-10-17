package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class DoubleSupplierExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        double forTestReturn = Math.random();
        DoubleSupplierEx doubleSupplierEx = () -> forTestReturn;
        Assertions.assertThat(doubleSupplierEx.execute()).isEqualTo(forTestReturn);

        DoubleSupplierEx doubleSupplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(doubleSupplierEx1::execute).isInstanceOf(IOException.class);

        DoubleSupplierEx doubleSupplierEx2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(doubleSupplierEx2::execute).isInstanceOf(InterruptedException.class);
    }

}
