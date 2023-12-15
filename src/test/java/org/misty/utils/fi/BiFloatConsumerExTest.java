package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class BiFloatConsumerExTest {

    @Test
    void execute() {
        BiFloatConsumerEx consumerEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(consumerEx1, IOException.class);

        BiFloatConsumerEx consumerEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(consumerEx2, InterruptedException.class);
    }

    void testThrow(BiFloatConsumerEx target, Class<? extends Exception> checkExceptionType) {
        float forTestArg1 = (float) Math.random();
        float forTestArg2 = (float) Math.random();

        AtomicReference<Float> tempArg1 = new AtomicReference<>();
        AtomicReference<Float> tempArg2 = new AtomicReference<>();
        BiFloatConsumerEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
