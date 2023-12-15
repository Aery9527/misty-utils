package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class FloatBinaryOperatorExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        float forTestArg1 = (float) Math.random();
        float forTestArg2 = (float) Math.random();
        FloatBinaryOperatorEx binaryOperatorEx = (arg1, arg2) -> arg1 + arg2;
        Assertions.assertThat(binaryOperatorEx.execute(forTestArg1, forTestArg2)).isEqualTo(forTestArg1 + forTestArg2);

        FloatBinaryOperatorEx binaryOperatorEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(binaryOperatorEx1, IOException.class);

        FloatBinaryOperatorEx binaryOperatorEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(binaryOperatorEx2, InterruptedException.class);
    }

    void testThrow(FloatBinaryOperatorEx target, Class<? extends Exception> checkExceptionType) {
        float forTestArg1 = (float) Math.random();
        float forTestArg2 = (float) Math.random();

        AtomicReference<Float> tempArg1 = new AtomicReference<>();
        AtomicReference<Float> tempArg2 = new AtomicReference<>();
        FloatBinaryOperatorEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
