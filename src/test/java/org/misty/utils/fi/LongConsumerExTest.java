package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class LongConsumerExTest {

    @Test
    void execute() {
        LongConsumerEx longConsumerEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(longConsumerEx1, IOException.class);

        LongConsumerEx longConsumerEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(longConsumerEx2, InterruptedException.class);
    }

    void testThrow(LongConsumerEx target, Class<? extends Exception> checkExceptionType) {
        long forTestArg = (long) (Math.random() * 100);

        AtomicLong tempArg = new AtomicLong();
        LongConsumerEx testWrap = (arg) -> {
            tempArg.set(arg);
            target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
