package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

class ToDoubleFunctionExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        String forTestArg = String.valueOf(Math.random());
        ToDoubleFunctionEx<String> toDoubleFunctionEx = (arg) -> Double.parseDouble(arg);
        Assertions.assertThat(toDoubleFunctionEx.execute(forTestArg)).isEqualTo(Double.parseDouble(forTestArg));

        ToDoubleFunctionEx<String> toDoubleFunctionEx1 = (arg) -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        testThrow(toDoubleFunctionEx1, IOException.class);

        ToDoubleFunctionEx<String> toDoubleFunctionEx2 = (arg) -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        testThrow(toDoubleFunctionEx2, InterruptedException.class);
    }

    void testThrow(ToDoubleFunctionEx<String> target, Class<? extends Exception> checkExceptionType) {
        String forTestArg = UUID.randomUUID().toString();

        AtomicReference<String> tempArg = new AtomicReference<>();
        ToDoubleFunctionEx<String> testWrap = (arg) -> {
            tempArg.set(arg);
            return target.execute(arg);
        };

        Assertions.assertThatThrownBy(() -> testWrap.execute(forTestArg)).isInstanceOf(checkExceptionType);
        Assertions.assertThat(tempArg.get()).isEqualTo(forTestArg);
    }

}
