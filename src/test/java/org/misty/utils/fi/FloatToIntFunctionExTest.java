package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class FloatToIntFunctionExTest {

    @Test
    void execute() {
        float forTestArg = (float) (Math.random() * 100);
        FloatToIntFunctionEx functionEx = (arg) -> (int) arg;
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo((int) forTestArg);

        FloatToIntFunctionEx functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        FloatToIntFunctionEx functionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(FloatToIntFunctionEx target, Class<? extends Exception> checkExceptionType) {
        float forTestArg = (float) (Math.random() * 100);

        AtomicReference<Float> tempArg = new AtomicReference<>();
        FloatToIntFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
