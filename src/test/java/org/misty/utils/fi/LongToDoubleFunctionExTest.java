package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class LongToDoubleFunctionExTest {

    @Test
    void execute() {
        long forTestArg = (long) (Math.random() * 100);
        LongToDoubleFunctionEx functionEx = (arg) -> arg;
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo(forTestArg);

        LongToDoubleFunctionEx functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        LongToDoubleFunctionEx functionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(LongToDoubleFunctionEx target, Class<? extends Exception> checkExceptionType) {
        long forTestArg = (long) (Math.random() * 100);

        AtomicLong tempArg = new AtomicLong();
        LongToDoubleFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
