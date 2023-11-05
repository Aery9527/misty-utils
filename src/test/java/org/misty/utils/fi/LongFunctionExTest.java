package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class LongFunctionExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        long forTestArg = (long) (Math.random() * 100);
        LongFunctionEx<String> longFunctionEx = (arg) -> String.valueOf(arg);
        Assertions.assertThat(longFunctionEx.execute(forTestArg)).isEqualTo(String.valueOf(forTestArg));

        LongFunctionEx<String> longFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(longFunctionEx1, IOException.class);

        LongFunctionEx<String> longFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(longFunctionEx2, InterruptedException.class);
    }

    void testThrow(LongFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        long forTestArg = (long) (Math.random() * 100);

        AtomicLong tempArg = new AtomicLong();
        LongFunctionEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
