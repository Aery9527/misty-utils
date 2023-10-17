package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class LongToDoubleFunctionExTest {

    @Test
    void execute() {
        long forTestArg = (long) (Math.random() * 100);
        LongToDoubleFunctionEx longToDoubleFunctionEx = (arg) -> arg;
        Assertions.assertThat(longToDoubleFunctionEx.execute(forTestArg)).isEqualTo(forTestArg);

        LongToDoubleFunctionEx longToDoubleFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(longToDoubleFunctionEx1, IOException.class);

        LongToDoubleFunctionEx longToDoubleFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(longToDoubleFunctionEx2, InterruptedException.class);
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
