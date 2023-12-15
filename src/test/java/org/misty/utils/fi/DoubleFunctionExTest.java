package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoubleFunctionExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        double forTestArg = Math.random();
        DoubleFunctionEx<String> functionEx = (arg) -> String.valueOf(arg);
        Assertions.assertThat(functionEx.execute(forTestArg)).isEqualTo(String.valueOf(forTestArg));

        DoubleFunctionEx<String> functionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(functionEx1, IOException.class);

        DoubleFunctionEx<String> functionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(functionEx2, InterruptedException.class);
    }

    void testThrow(DoubleFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        double forTestArg = Math.random();

        AtomicReference<Double> tempArg = new AtomicReference<>();
        DoubleFunctionEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
