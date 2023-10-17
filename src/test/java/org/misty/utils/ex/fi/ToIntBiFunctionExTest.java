package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ToIntBiFunctionExTest {

    @Test
    void execute() {
        String forTestArg1 = String.valueOf((int) (Math.random() * 100));
        String forTestArg2 = String.valueOf((int) (Math.random() * 100));
        ToIntBiFunctionEx<String, String> toIntBiFunctionEx = (arg1, arg2) -> Integer.parseInt(arg1) + Integer.parseInt(arg2);
        Assertions.assertThat(toIntBiFunctionEx.execute(forTestArg1, forTestArg2))
                .isEqualTo(Integer.parseInt(forTestArg1) + Integer.parseInt(forTestArg2));

        ToIntBiFunctionEx<String, String> toIntBiFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(toIntBiFunctionEx1, IOException.class);

        ToIntBiFunctionEx<String, String> toIntBiFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(toIntBiFunctionEx2, InterruptedException.class);
    }

    void testThrow(ToIntBiFunctionEx<String, String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg1 = UUID.randomUUID().toString();
        String forTestArg2 = UUID.randomUUID().toString();

        AtomicReference<String> tempArg1 = new AtomicReference<>();
        AtomicReference<String> tempArg2 = new AtomicReference<>();
        ToIntBiFunctionEx<String, String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
