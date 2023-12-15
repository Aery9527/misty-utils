package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class BiDoubleFunctionExTest {

    @Test
    void execute() {
        double forTestArg1 = Math.random();
        double forTestArg2 = Math.random();
        BiDoubleFunctionEx<String> biFunctionEx = (arg1, arg2) -> String.valueOf(arg1 + arg2);
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2)).isEqualTo(String.valueOf(forTestArg1 + forTestArg2));

        BiDoubleFunctionEx<String> biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        BiDoubleFunctionEx<String> biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(BiDoubleFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        double forTestArg1 = Math.random();
        double forTestArg2 = Math.random();

        AtomicReference<Double> tempArg1 = new AtomicReference<>();
        AtomicReference<Double> tempArg2 = new AtomicReference<>();
        BiDoubleFunctionEx<String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
