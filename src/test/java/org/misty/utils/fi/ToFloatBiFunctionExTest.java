package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ToFloatBiFunctionExTest {

    @Test
    void execute() {
        String forTestArg1 = String.valueOf(Math.random());
        String forTestArg2 = String.valueOf(Math.random());
        ToFloatBiFunctionEx<String, String> biFunctionEx = (arg1, arg2) -> Float.parseFloat(arg1) + Float.parseFloat(arg2);
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2))
                .isEqualTo(Float.parseFloat(forTestArg1) + Float.parseFloat(forTestArg2));

        ToFloatBiFunctionEx<String, String> biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        ToFloatBiFunctionEx<String, String> biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(ToFloatBiFunctionEx<String, String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg1 = UUID.randomUUID().toString();
        String forTestArg2 = UUID.randomUUID().toString();

        AtomicReference<String> tempArg1 = new AtomicReference<>();
        AtomicReference<String> tempArg2 = new AtomicReference<>();
        ToFloatBiFunctionEx<String, String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
