package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class BiShortToLongFunctionExTest {

    @Test
    void execute() {
        short forTestArg1 = (short) (Math.random() * 100);
        short forTestArg2 = (short) (Math.random() * 100);
        BiShortToLongFunctionEx biFunctionEx = (arg1, arg2) -> (long) (arg1 + arg2);
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2))
                .isEqualTo((long) (forTestArg1 + forTestArg2));

        BiShortToLongFunctionEx biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        BiShortToLongFunctionEx biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(BiShortToLongFunctionEx target, Class<? extends Exception> checkExceptionType) {
        short forTestArg1 = (short) (Math.random() * 100);
        short forTestArg2 = (short) (Math.random() * 100);

        AtomicReference<Short> tempArg1 = new AtomicReference<>();
        AtomicReference<Short> tempArg2 = new AtomicReference<>();
        BiShortToLongFunctionEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
