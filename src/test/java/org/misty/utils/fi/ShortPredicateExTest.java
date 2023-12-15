package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class ShortPredicateExTest {

    @Test
    void execute() {
        boolean forTestReturn = (int) (Math.random() * 2) == 0;
        ShortPredicateEx predicateEx = (arg) -> forTestReturn;
        Assertions.assertThat(predicateEx.execute((short) 0)).isEqualTo(forTestReturn);

        ShortPredicateEx predicateEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(predicateEx1, IOException.class);

        ShortPredicateEx predicateEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(predicateEx2, InterruptedException.class);
    }

    void testThrow(ShortPredicateEx target, Class<? extends Exception> checkExceptionType) {
        short forTestArg = (short) (Math.random() * 100);

        AtomicReference<Short> tempArg = new AtomicReference<>();
        ShortPredicateEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
