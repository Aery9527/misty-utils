package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class IntFunctionExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        int forTestArg = (int) (Math.random() * 100);
        IntFunctionEx<String> intFunctionEx = (arg) -> String.valueOf(arg);
        Assertions.assertThat(intFunctionEx.execute(forTestArg)).isEqualTo(String.valueOf(forTestArg));

        IntFunctionEx<String> intFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(intFunctionEx1, IOException.class);

        IntFunctionEx<String> intFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(intFunctionEx2, InterruptedException.class);
    }

    void testThrow(IntFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        int forTestArg = (int) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        IntFunctionEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
