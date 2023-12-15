package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class FloatToDoubleFunctionExTest {

    @Test
    void execute() {
        float forTestArg = (float) (Math.random() * 100);
        FloatToDoubleFunctionEx functionEx = (arg) -> arg;
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo(forTestArg);

        FloatToDoubleFunctionEx functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        FloatToDoubleFunctionEx functionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(FloatToDoubleFunctionEx target, Class<? extends Exception> checkExceptionType) {
        float forTestArg = (float) (Math.random() * 100);

        AtomicReference<Float> tempArg = new AtomicReference<>();
        FloatToDoubleFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
