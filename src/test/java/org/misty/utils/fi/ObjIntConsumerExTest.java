package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class ObjIntConsumerExTest {

    @Test
    void execute() {
        ObjIntConsumerEx<String> objIntConsumerEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(objIntConsumerEx1, IOException.class);

        ObjIntConsumerEx<String> objIntConsumerEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(objIntConsumerEx2, InterruptedException.class);
    }

    void testThrow(ObjIntConsumerEx<String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg1 = UUID.randomUUID().toString();
        int forTestArg2 = (int) (Math.random() * 100);

        AtomicReference<String> tempArg1 = new AtomicReference<>();
        AtomicInteger tempArg2 = new AtomicInteger();
        ObjIntConsumerEx<String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
