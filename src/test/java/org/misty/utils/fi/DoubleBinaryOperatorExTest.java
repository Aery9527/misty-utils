package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoubleBinaryOperatorExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        double forTestArg1 = Math.random();
        double forTestArg2 = Math.random();
        DoubleBinaryOperatorEx doubleBinaryOperatorEx = (arg1, arg2) -> arg1 + arg2;
        Assertions.assertThat(doubleBinaryOperatorEx.execute(forTestArg1, forTestArg2)).isEqualTo(forTestArg1 + forTestArg2);

        DoubleBinaryOperatorEx doubleBinaryOperatorEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(doubleBinaryOperatorEx1, IOException.class);

        DoubleBinaryOperatorEx doubleBinaryOperatorEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(doubleBinaryOperatorEx2, InterruptedException.class);
    }

    void testThrow(DoubleBinaryOperatorEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg1 = Math.random();
        double forTestArg2 = Math.random();

        AtomicReference<Double> tempArg1 = new AtomicReference<>();
        AtomicReference<Double> tempArg2 = new AtomicReference<>();
        DoubleBinaryOperatorEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
