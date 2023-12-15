package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoubleUnaryOperatorExTest {

    @Test
    void execute() {
        double forTestArg = Math.random();
        DoubleUnaryOperatorEx unaryOperatorEx = (arg) -> arg;
        Assertions.assertThat(unaryOperatorEx.execute(forTestArg)).isEqualTo(forTestArg);

        DoubleUnaryOperatorEx unaryOperatorEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx1, IOException.class);

        DoubleUnaryOperatorEx unaryOperatorEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx2, InterruptedException.class);
    }

    void testThrow(DoubleUnaryOperatorEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg = Math.random();

        AtomicReference<Double> tempArg = new AtomicReference<>();
        DoubleUnaryOperatorEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
