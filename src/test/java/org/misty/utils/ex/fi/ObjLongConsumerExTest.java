package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

class ObjLongConsumerExTest {

    @Test
    void execute() {
        ObjLongConsumerEx<String> objLongConsumerEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(objLongConsumerEx1, IOException.class);

        ObjLongConsumerEx<String> objLongConsumerEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(objLongConsumerEx2, InterruptedException.class);
    }

    void testThrow(ObjLongConsumerEx<String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg1 = UUID.randomUUID().toString();
        long forTestArg2 = (long) (Math.random() * 100);

        AtomicReference<String> tempArg1 = new AtomicReference<>();
        AtomicLong tempArg2 = new AtomicLong();
        ObjLongConsumerEx<String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
