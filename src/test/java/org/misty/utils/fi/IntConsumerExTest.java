package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class IntConsumerExTest {

    @Test
    void execute() {
        IntConsumerEx intConsumerEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(intConsumerEx1, IOException.class);

        IntConsumerEx intConsumerEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(intConsumerEx2, InterruptedException.class);
    }

    void testThrow(IntConsumerEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg = (int) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        IntConsumerEx testWrap = (arg) -> {
            tempArg.set(arg);
            target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
