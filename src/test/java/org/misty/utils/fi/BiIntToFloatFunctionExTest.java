package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class BiIntToFloatFunctionExTest {

    @Test
    void execute() {
        int forTestArg1 = (int) (Math.random() * 100);
        int forTestArg2 = (int) (Math.random() * 100);
        BiIntToFloatFunctionEx biFunctionEx = (arg1, arg2) -> (float) (arg1 + arg2);
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2))
                .isEqualTo((float) (forTestArg1 + forTestArg2));

        BiIntToFloatFunctionEx biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        BiIntToFloatFunctionEx biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(BiIntToFloatFunctionEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg1 = (int) (Math.random() * 100);
        int forTestArg2 = (int) (Math.random() * 100);

        AtomicInteger tempArg1 = new AtomicInteger();
        AtomicInteger tempArg2 = new AtomicInteger();
        BiIntToFloatFunctionEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
