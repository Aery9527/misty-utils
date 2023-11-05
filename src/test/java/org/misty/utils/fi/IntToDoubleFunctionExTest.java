package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class IntToDoubleFunctionExTest {

    @Test
    void execute() {
        int forTestArg = (int) (Math.random() * 100);
        IntToDoubleFunctionEx intToDoubleFunctionEx = (arg) -> arg;
        Assertions.assertThat(intToDoubleFunctionEx.execute(forTestArg)).isEqualTo(forTestArg);

        IntToDoubleFunctionEx intToDoubleFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(intToDoubleFunctionEx1, IOException.class);

        IntToDoubleFunctionEx intToDoubleFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(intToDoubleFunctionEx2, InterruptedException.class);
    }

    void testThrow(IntToDoubleFunctionEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg = (int) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        IntToDoubleFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
