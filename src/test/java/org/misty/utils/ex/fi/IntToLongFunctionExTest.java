package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class IntToLongFunctionExTest {

    @Test
    void execute() {
        int forTestArg = (int) (Math.random() * 100);
        IntToLongFunctionEx intToLongFunctionEx = (arg) -> arg;
        Assertions.assertThat(intToLongFunctionEx.execute(forTestArg)).isEqualTo(forTestArg);

        IntToLongFunctionEx intToLongFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(intToLongFunctionEx1, IOException.class);

        IntToLongFunctionEx intToLongFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(intToLongFunctionEx2, InterruptedException.class);
    }

    void testThrow(IntToLongFunctionEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg = (int) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        IntToLongFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
