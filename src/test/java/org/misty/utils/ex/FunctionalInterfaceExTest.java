package org.misty.utils.ex;

import java.io.IOException;

class FunctionalInterfaceExTest {

    public static <ReturnType> ReturnType throwIOException() throws IOException {
        throw new IOException();
    }

    public static <ReturnType> ReturnType throwInterruptedException() throws InterruptedException {
        throw new InterruptedException();
    }

    public static <ReturnType> ReturnType thrown(IOException e) throws IOException, InterruptedException {
        throw e;
    }

    public static <ReturnType> ReturnType thrown(InterruptedException e) throws IOException, InterruptedException {
        throw e;
    }

}
