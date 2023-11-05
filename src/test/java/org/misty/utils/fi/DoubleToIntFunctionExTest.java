package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoubleToIntFunctionExTest {

    @Test
    void execute() {
        double forTestArg = Math.random() * 100;
        DoubleToIntFunctionEx doubleToIntFunctionEx = (arg) -> (int) arg;
        Assertions.assertThat(doubleToIntFunctionEx.execute(forTestArg)).isEqualTo((int) forTestArg);

        DoubleToIntFunctionEx doubleToIntFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(doubleToIntFunctionEx1, IOException.class);

        DoubleToIntFunctionEx doubleToIntFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(doubleToIntFunctionEx2, InterruptedException.class);
    }

    void testThrow(DoubleToIntFunctionEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg = Math.random() * 100;

        AtomicReference<Double> tempArg = new AtomicReference<>();
        DoubleToIntFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
