package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class IntUnaryOperatorExTest {

    @Test
    void execute() {
        int forTestArg = (int) (Math.random() * 100);
        IntUnaryOperatorEx unaryOperatorEx = (arg) -> arg;
        Assertions.assertThat(unaryOperatorEx.execute(forTestArg)).isEqualTo(forTestArg);

        IntUnaryOperatorEx unaryOperatorEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx1, IOException.class);

        IntUnaryOperatorEx unaryOperatorEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx2, InterruptedException.class);
    }

    void testThrow(IntUnaryOperatorEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg = (int) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        IntUnaryOperatorEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
