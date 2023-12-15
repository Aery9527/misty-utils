package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoubleToFloatFunctionExTest {

    @Test
    void execute() {
        double forTestArg = Math.random() * 100;
        DoubleToFloatFunctionEx functionEx = (arg) -> (float) arg;
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo((float) forTestArg);

        DoubleToFloatFunctionEx functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        DoubleToFloatFunctionEx functionEx2
                = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(DoubleToFloatFunctionEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg = Math.random() * 100;

        AtomicReference<Double> tempArg = new AtomicReference<>();
        DoubleToFloatFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
