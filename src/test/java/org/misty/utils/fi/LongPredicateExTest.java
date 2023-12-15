package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class LongPredicateExTest {

    @Test
    void execute() {
        long forTestReturn = (long) (Math.random() * 100);
        LongPredicateEx predicateEx = (arg) -> arg % 2 == 0;
        Assertions.assertThat(predicateEx.execute(forTestReturn)).isEqualTo(forTestReturn % 2 == 0);

        LongPredicateEx predicateEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(predicateEx1, IOException.class);

        LongPredicateEx predicateEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(predicateEx2, InterruptedException.class);
    }

    void testThrow(LongPredicateEx target, Class<? extends Exception> checkExceptionType) {
        long forTestArg = (long) (Math.random() * 100);

        AtomicLong tempArg = new AtomicLong();
        LongPredicateEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
