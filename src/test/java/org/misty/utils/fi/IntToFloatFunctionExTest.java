package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class IntToFloatFunctionExTest {

    @Test
    void execute() {
        int forTestArg = (int) (Math.random() * 100);
        IntToFloatFunctionEx functionEx = (arg) -> arg;
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo(forTestArg);

        IntToFloatFunctionEx functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        IntToFloatFunctionEx functionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(IntToFloatFunctionEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg = (int) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        IntToFloatFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
