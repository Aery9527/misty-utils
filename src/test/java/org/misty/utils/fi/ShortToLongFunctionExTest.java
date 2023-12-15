package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class ShortToLongFunctionExTest {

    @Test
    void execute() {
        short forTestArg = (short) (Math.random() * 100);
        ShortToLongFunctionEx functionEx = (arg) -> (long) arg;
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo((long) forTestArg);

        ShortToLongFunctionEx functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        ShortToLongFunctionEx functionEx2
                = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(ShortToLongFunctionEx target, Class<? extends Exception> checkExceptionType) {
        short forTestArg = (short) (Math.random() * 100);

        AtomicReference<Short> tempArg = new AtomicReference<>();
        ShortToLongFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
