package org.misty.utils.fi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class RunnableExTest {

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void execute() {
        RunnableEx runnableEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(runnableEx1::execute).isInstanceOf(IOException.class);

        RunnableEx runnableEx2 = () -> FunctionalInterfaceExTest.throwInterruptedException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(runnableEx2::execute).isInstanceOf(InterruptedException.class);
    }

}
