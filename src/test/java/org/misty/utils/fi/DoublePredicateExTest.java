package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class DoublePredicateExTest {

    @Test
    void execute() {
        boolean forTestReturn = (int) (Math.random() * 2) == 0;
        DoublePredicateEx doublePredicateEx = (arg) -> forTestReturn;
        Assertions.assertThat(doublePredicateEx.execute(0)).isEqualTo(forTestReturn);

        DoublePredicateEx doublePredicateEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(doublePredicateEx1, IOException.class);

        DoublePredicateEx doublePredicateEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(doublePredicateEx2, InterruptedException.class);
    }

    void testThrow(DoublePredicateEx target, Class<? extends Exception> checkExceptionType) {
        double forTestArg = Math.random();

        AtomicReference<Double> tempArg = new AtomicReference<>();
        DoublePredicateEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
