package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class FloatPredicateExTest {

    @Test
    void execute() {
        boolean forTestReturn = (int) (Math.random() * 2) == 0;
        FloatPredicateEx predicateEx = (arg) -> forTestReturn;
        Assertions.assertThat(predicateEx.execute(0)).isEqualTo(forTestReturn);

        FloatPredicateEx predicateEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(predicateEx1, IOException.class);

        FloatPredicateEx predicateEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(predicateEx2, InterruptedException.class);
    }

    void testThrow(FloatPredicateEx target, Class<? extends Exception> checkExceptionType) {
        float forTestArg = (float) Math.random();

        AtomicReference<Float> tempArg = new AtomicReference<>();
        FloatPredicateEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
