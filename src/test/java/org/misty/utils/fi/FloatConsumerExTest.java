package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class FloatConsumerExTest {

    @Test
    void execute() {
        FloatConsumerEx consumerEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(consumerEx1, IOException.class);

        FloatConsumerEx consumerEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(consumerEx2, InterruptedException.class);
    }

    void testThrow(FloatConsumerEx target, Class<? extends Exception> checkExceptionType) {
        float forTestArg = (float) Math.random();

        AtomicReference<Float> tempArg = new AtomicReference<>();
        FloatConsumerEx testWrap = (arg) -> {
            tempArg.set(arg);
            target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
