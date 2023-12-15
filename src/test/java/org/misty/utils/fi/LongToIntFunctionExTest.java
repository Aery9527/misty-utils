package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class LongToIntFunctionExTest {

    @Test
    void execute() {
        long forTestArg = (long) (Math.random() * 100);
        LongToIntFunctionEx functionEx = (arg) -> (int) arg;
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo((int) forTestArg);

        LongToIntFunctionEx functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        LongToIntFunctionEx functionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(LongToIntFunctionEx target, Class<? extends Exception> checkExceptionType) {
        long forTestArg = (long) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        LongToIntFunctionEx testWrap = (arg) -> {
            tempArg.set((int) arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo((int) forTestArg);
    }

}
