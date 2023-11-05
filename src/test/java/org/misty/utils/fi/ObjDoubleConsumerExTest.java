package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ObjDoubleConsumerExTest {

    @Test
    void execute() {
        ObjDoubleConsumerEx<String> objDoubleConsumerEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(objDoubleConsumerEx1, IOException.class);

        ObjDoubleConsumerEx<String> objDoubleConsumerEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(objDoubleConsumerEx2, InterruptedException.class);
    }

    void testThrow(ObjDoubleConsumerEx<String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg1 = UUID.randomUUID().toString();
        double forTestArg2 = Math.random();

        AtomicReference<String> tempArg1 = new AtomicReference<>();
        AtomicReference<Double> tempArg2 = new AtomicReference<>();
        ObjDoubleConsumerEx<String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
