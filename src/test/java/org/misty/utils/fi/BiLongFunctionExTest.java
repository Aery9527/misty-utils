package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class BiLongFunctionExTest {

    @Test
    void execute() {
        long forTestArg1 = (long) (Math.random() * 100);
        long forTestArg2 = (long) (Math.random() * 100);
        BiLongFunctionEx<String> biFunctionEx = (arg1, arg2) -> String.valueOf(arg1 + arg2);
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2)).isEqualTo(String.valueOf(forTestArg1 + forTestArg2));

        BiLongFunctionEx<String> biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        BiLongFunctionEx<String> biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(BiLongFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        long forTestArg1 = (long) (Math.random() * 100);
        long forTestArg2 = (long) (Math.random() * 100);

        AtomicLong tempArg1 = new AtomicLong();
        AtomicLong tempArg2 = new AtomicLong();
        BiLongFunctionEx<String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
