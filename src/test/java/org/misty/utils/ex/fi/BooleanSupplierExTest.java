package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BooleanSupplierExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        boolean forTestReturn = (int) (Math.random() * 2) == 0;
        BooleanSupplierEx booleanSupplierEx = () -> forTestReturn;
        Assertions.assertThat(booleanSupplierEx.execute()).isEqualTo(forTestReturn);

        BooleanSupplierEx booleanSupplierEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(booleanSupplierEx1::execute).isInstanceOf(IOException.class);

        BooleanSupplierEx booleanSupplierE2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(booleanSupplierE2::execute).isInstanceOf(InterruptedException.class);
    }

}
