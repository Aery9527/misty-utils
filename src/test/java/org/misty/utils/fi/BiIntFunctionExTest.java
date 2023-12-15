package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class BiIntFunctionExTest {

    @Test
    void execute() {
        int forTestArg1 = (int) (Math.random() * 100);
        int forTestArg2 = (int) (Math.random() * 100);
        BiIntFunctionEx<String> biFunctionEx = (arg1, arg2) -> String.valueOf(arg1 + arg2);
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2)).isEqualTo(String.valueOf(forTestArg1 + forTestArg2));

        BiIntFunctionEx<String> biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        BiIntFunctionEx<String> biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(BiIntFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        int forTestArg1 = (int) (Math.random() * 100);
        int forTestArg2 = (int) (Math.random() * 100);

        AtomicInteger tempArg1 = new AtomicInteger();
        AtomicInteger tempArg2 = new AtomicInteger();
        BiIntFunctionEx<String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
