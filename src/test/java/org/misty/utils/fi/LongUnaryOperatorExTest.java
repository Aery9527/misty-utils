package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class LongUnaryOperatorExTest {

    @Test
    void execute() {
        long forTestArg = (long) (Math.random() * 100);
        LongUnaryOperatorEx unaryOperatorEx = (arg) -> arg;
        Assertions.assertThat(unaryOperatorEx.execute(forTestArg)).isEqualTo(forTestArg);

        LongUnaryOperatorEx unaryOperatorEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx1, IOException.class);

        LongUnaryOperatorEx unaryOperatorEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx2, InterruptedException.class);
    }

    void testThrow(LongUnaryOperatorEx target, Class<? extends Exception> checkExceptionType) {
        long forTestArg = (long) (Math.random() * 100);

        AtomicLong tempArg = new AtomicLong();
        LongUnaryOperatorEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
