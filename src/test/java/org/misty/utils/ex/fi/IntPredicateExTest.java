package org.misty.utils.ex.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class IntPredicateExTest {

    @Test
    void execute() {
        int forTestReturn = (int) (Math.random() * 100);
        IntPredicateEx intPredicateEx = (arg) -> arg % 2 == 0;
        Assertions.assertThat(intPredicateEx.execute(forTestReturn)).isEqualTo(forTestReturn % 2 == 0);

        IntPredicateEx intPredicateEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(intPredicateEx1, IOException.class);

        IntPredicateEx intPredicateEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(intPredicateEx2, InterruptedException.class);
    }

    void testThrow(IntPredicateEx target, Class<? extends Exception> checkExceptionType) {
        int forTestArg = (int) (Math.random() * 100);

        AtomicInteger tempArg = new AtomicInteger();
        IntPredicateEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
