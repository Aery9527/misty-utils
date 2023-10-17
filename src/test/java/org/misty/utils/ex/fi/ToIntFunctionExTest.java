package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ToIntFunctionExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        String forTestArg = String.valueOf((int) (Math.random() * 100));
        ToIntFunctionEx<String> toIntFunctionEx = (arg) -> Integer.parseInt(arg);
        Assertions.assertThat(toIntFunctionEx.execute(forTestArg)).isEqualTo(Integer.parseInt(forTestArg));

        ToIntFunctionEx<String> ToIntFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(ToIntFunctionEx1, IOException.class);

        ToIntFunctionEx<String> ToIntFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(ToIntFunctionEx2, InterruptedException.class);
    }

    void testThrow(ToIntFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg = UUID.randomUUID().toString();

        AtomicReference<String> tempArg = new AtomicReference<>();
        ToIntFunctionEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
