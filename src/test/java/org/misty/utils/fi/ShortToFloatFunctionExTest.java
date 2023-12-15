package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

class ShortToFloatFunctionExTest {

    @Test
    void execute() {
        short forTestArg = (short) (Math.random() * 100);
        ShortToFloatFunctionEx unaryOperatorEx = (arg) -> arg;
        Assertions.assertThat(unaryOperatorEx.execute(forTestArg)).isEqualTo(forTestArg);

        ShortToFloatFunctionEx unaryOperatorEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx1, IOException.class);

        ShortToFloatFunctionEx unaryOperatorEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(unaryOperatorEx2, InterruptedException.class);
    }

    void testThrow(ShortToFloatFunctionEx target, Class<? extends Exception> checkExceptionType) {
        short forTestArg = (short) (Math.random() * 100);

        AtomicReference<Short> tempArg = new AtomicReference<>();
        ShortToFloatFunctionEx testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
