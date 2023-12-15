package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ToShortBiFunctionExTest {

    @Test
    void execute() {
        String forTestArg1 = String.valueOf((short) (Math.random() * 100));
        String forTestArg2 = String.valueOf((short) (Math.random() * 100));
        ToShortBiFunctionEx<String, String> biFunctionEx = (arg1, arg2) -> (short) (Short.parseShort(arg1) + Short.parseShort(arg2));
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2))
                .isEqualTo((short) (Short.parseShort(forTestArg1) + Short.parseShort(forTestArg2)));

        ToShortBiFunctionEx<String, String> biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        ToShortBiFunctionEx<String, String> biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(ToShortBiFunctionEx<String, String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg1 = UUID.randomUUID().toString();
        String forTestArg2 = UUID.randomUUID().toString();

        AtomicReference<String> tempArg1 = new AtomicReference<>();
        AtomicReference<String> tempArg2 = new AtomicReference<>();
        ToShortBiFunctionEx<String, String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
