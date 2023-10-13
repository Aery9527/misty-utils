package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class RunnableExTest {

    @Test
    void execute() {
        RunnableEx<IOException> runnableEx1 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(runnableEx1::execute)
                .isInstanceOf(IOException.class);

        // When the declared exception type differs from the thrown exception type, no error occurs. It's quite magical.
        RunnableEx<InterruptedException> runnableEx2 = () -> FunctionalInterfaceExTest.throwIOException(); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(runnableEx2::execute)
                .isInstanceOf(IOException.class);

        RunnableEx<IOException> runnableEx3 = () -> FunctionalInterfaceExTest.thrown(new IOException()); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(runnableEx3::execute)
                .isInstanceOf(IOException.class);

        RunnableEx<IOException> runnableEx4 = () -> FunctionalInterfaceExTest.thrown(new InterruptedException()); // there is no need for a try-catch.
        Assertions.assertThatThrownBy(runnableEx4::execute)
                .isInstanceOf(InterruptedException.class);
    }

}
