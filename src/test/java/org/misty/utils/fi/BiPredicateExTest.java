package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class BiPredicateExTest {

    @Test
    void execute() {
        boolean forTestReturn = (int) (Math.random() * 2) == 0;
        BiPredicateEx<String, String> biPredicateEx = (arg1, arg2) -> forTestReturn;
        Assertions.assertThat(biPredicateEx.execute(null, null)).isEqualTo(forTestReturn);

        BiPredicateEx<String, String> biPredicateEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(biPredicateEx1, IOException.class);

        BiPredicateEx<String, String> biPredicateEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(biPredicateEx2, InterruptedException.class);
    }

    void testThrow(BiPredicateEx<String, String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg1 = UUID.randomUUID().toString();
        String forTestArg2 = UUID.randomUUID().toString();

        AtomicReference<String> tempArg1 = new AtomicReference<>();
        AtomicReference<String> tempArg2 = new AtomicReference<>();
        BiPredicateEx<String, String> testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
