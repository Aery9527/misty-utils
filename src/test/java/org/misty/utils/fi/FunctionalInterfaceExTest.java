package org.misty.utils.fi;

import java.io.IOException;

class FunctionalInterfaceExTest {

    public static <ReturnType> ReturnType throwIOException() throws IOException, InterruptedException {
        throw new IOException();
    }

    public static <ReturnType> ReturnType throwInterruptedException() throws IOException, InterruptedException {
        throw new InterruptedException();
    }

}
