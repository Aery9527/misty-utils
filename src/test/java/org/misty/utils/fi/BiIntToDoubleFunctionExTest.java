package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class BiIntToDoubleFunctionExTest {

    @Test
    void execute() {
        int forTestArg1 = (int) (Math.random() * 100);
        int forTestArg2 = (int) (Math.random() * 100);
        BiIntToDoubleFunctionEx biFunctionEx = (arg1, arg2) -> arg1 + arg2;
        Assertions.assertThat(biFunctionEx.execute(forTestArg1, forTestArg2)).isEqualTo(forTestArg1 + forTestArg2);

        BiIntToDoubleFunctionEx biFunctionEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biFunctionEx1, IOException.class);

        BiIntToDoubleFunctionEx biFunctionEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biFunctionEx2, InterruptedException.class);
    }

    void testThrow(BiIntToDoubleFunctionEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg1 = (int) (Math.random() * 100);
        int forTestArg2 = (int) (Math.random() * 100);

        AtomicInteger tempArg1 = new AtomicInteger();
        AtomicInteger tempArg2 = new AtomicInteger();
        BiIntToDoubleFunctionEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
