package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoubleConsumerExTest {

    @Test
    void execute() {
        DoubleConsumerEx doubleConsumerEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(doubleConsumerEx1, IOException.class);

        DoubleConsumerEx doubleConsumerEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(doubleConsumerEx2, InterruptedException.class);
    }

    void testThrow(DoubleConsumerEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg = Math.random();

        AtomicReference<Double> tempArg = new AtomicReference<>();
        DoubleConsumerEx testWrap = (arg) -> {
            tempArg.set(arg);
            target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
