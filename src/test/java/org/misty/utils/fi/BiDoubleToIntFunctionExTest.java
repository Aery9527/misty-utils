package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class BiDoubleToIntFunctionExTest {

    @Test
    void execute() {
        double forTestArg1 = Math.random() * 100;
        double forTestArg2 = Math.random() * 100;
        BiDoubleToIntFunctionEx biFunctionEx = (arg1, arg2) -> (int) (arg1 + arg2);
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2))
                .isEqualTo((int) (forTestArg1 + forTestArg2));

        BiDoubleToIntFunctionEx biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        BiDoubleToIntFunctionEx biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(BiDoubleToIntFunctionEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg1 = Math.random();
        double forTestArg2 = Math.random();

        AtomicReference<Double> tempArg1 = new AtomicReference<>();
        AtomicReference<Double> tempArg2 = new AtomicReference<>();
        BiDoubleToIntFunctionEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
