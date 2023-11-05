package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ConsumerExTest {

    @Test
    void execute() {
        ConsumerEx<String> consumerEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(consumerEx1, IOException.class);

        ConsumerEx<String> consumerEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(consumerEx2, InterruptedException.class);
    }

    void testThrow(ConsumerEx<String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg = UUID.randomUUID().toString();

        AtomicReference<String> tempArg = new AtomicReference<>();
        ConsumerEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
