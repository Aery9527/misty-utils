package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class ShortFunctionExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        short forTestArg = (short) (Math.random() * 100);
        ShortFunctionEx<String> functionEx = (arg) -> String.valueOf(arg);
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo(String.valueOf(forTestArg));

        ShortFunctionEx<String> functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        ShortFunctionEx<String> functionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(ShortFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        short forTestArg = (short) (Math.random() * 100);

        AtomicReference<Short> tempArg = new AtomicReference<>();
        ShortFunctionEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
