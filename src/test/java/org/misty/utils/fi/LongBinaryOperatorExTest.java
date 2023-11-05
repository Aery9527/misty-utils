package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

class LongBinaryOperatorExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        long forTestArg1 = (long) (Math.random() * 100);
        long forTestArg2 = (long) (Math.random() * 100);
        LongBinaryOperatorEx longBinaryOperatorEx = (arg1, arg2) -> arg1 + arg2;
        Assertions.assertThat(longBinaryOperatorEx.execute(forTestArg1, forTestArg2)).isEqualTo(forTestArg1 + forTestArg2);

        LongBinaryOperatorEx longBinaryOperatorEx1 = (arg1, arg2) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(longBinaryOperatorEx1, IOException.class);

        LongBinaryOperatorEx longBinaryOperatorEx2 = (arg1, arg2) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(longBinaryOperatorEx2, InterruptedException.class);
    }

    void testThrow(LongBinaryOperatorEx target, Class<? extends Exception> checkExceptionType) {
        long forTestArg1 = (long) (Math.random() * 100);
        long forTestArg2 = (long) (Math.random() * 100);

        AtomicLong tempArg1 = new AtomicLong();
        AtomicLong tempArg2 = new AtomicLong();
        LongBinaryOperatorEx testWrap = (arg1, arg2) -> {
            tempArg1.set(arg1);
            tempArg2.set(arg2);
            return target.execute(arg1, arg2);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg1, forTestArg2)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg1.get()).isEqualTo(forTestArg1);
        Assertions.assertThat(tempArg2.get()).isEqualTo(forTestArg2);
    }

}
