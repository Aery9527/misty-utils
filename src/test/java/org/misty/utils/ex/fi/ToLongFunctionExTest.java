package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ToLongFunctionExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        String forTestArg = String.valueOf((long) (Math.random() * 100));
        ToLongFunctionEx<String> toLongFunctionEx = (arg) -> Long.parseLong(arg);
        Assertions.assertThat(toLongFunctionEx.execute(forTestArg)).isEqualTo(Long.parseLong(forTestArg));

        ToLongFunctionEx<String> toLongFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(toLongFunctionEx1, IOException.class);

        ToLongFunctionEx<String> toLongFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(toLongFunctionEx2, InterruptedException.class);
    }

    void testThrow(ToLongFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg = UUID.randomUUID().toString();

        AtomicReference<String> tempArg = new AtomicReference<>();
        ToLongFunctionEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
