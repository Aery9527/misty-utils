package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoubleToLongFunctionExTest {

    @Test
    void execute() {
        double forTestArg = Math.random() * 100;
        DoubleToLongFunctionEx doubleToLongFunctionEx = (arg) -> (int) arg;
        Assertions.assertThat(doubleToLongFunctionEx.execute(forTestArg)).isEqualTo((int) forTestArg);

        DoubleToLongFunctionEx doubleToLongFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(doubleToLongFunctionEx1, IOException.class);

        DoubleToLongFunctionEx doubleToLongFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(doubleToLongFunctionEx2, InterruptedException.class);
    }

    void testThrow(DoubleToLongFunctionEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg = Math.random() * 100;

        AtomicReference<Double> tempArg = new AtomicReference<>();
        DoubleToLongFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
