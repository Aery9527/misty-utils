package org.misty.utils;

import org.misty.utils.verify.Verifier;

import java.util.Arrays;

public class StackFetcher {

    public static StackTraceElement fetchCurrentStack() {
        return fetchStack(1);
    }

    public static StackTraceElement fetchPreviousStack() {
        return fetchStack(2);
    }

    public static StackTraceElement fetchStack(int offset) {
        Verifier.requireIntMoreThanInclusive("offset", offset, 0);
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        StackTraceElement ste = steArray[offset + 2];
        return ste;
    }

    public static StringBuilder fetchStacksPretty() {
        String prefix = "\tat ";
        String suffix = System.lineSeparator();
        return fetchStacksPretty(1, prefix, suffix);
    }

    public static StringBuilder fetchStacksPretty(String prefix) {
        String suffix = System.lineSeparator();
        return fetchStacksPretty(1, prefix, suffix);
    }

    public static StringBuilder fetchStacksPretty(String prefix, String suffix) {
        return fetchStacksPretty(1, prefix, suffix);
    }

    public static StringBuilder fetchStacksPretty(int offset, String prefix, String suffix) {
        StackTraceElement[] steArray = fetchStacks(1 + offset);
        return toPrettyString(prefix, suffix, steArray);
    }

    public static StringBuilder toPrettyString(String prefix, String suffix, StackTraceElement... steArray) {
        StringBuilder sb = new StringBuilder();

        for (StackTraceElement ste : steArray) {
            sb.append(prefix + ste.toString() + suffix);
        }

        return sb;
    }

    private static StackTraceElement[] fetchStacks(int offset) {
        Verifier.requireIntMoreThanInclusive("offset", offset, 0);
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        StackTraceElement[] newSteArray = Arrays.copyOfRange(steArray, offset + 2, steArray.length - 1);
        return newSteArray;
    }

}
