package org.misty.utils;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

class StackFetcherTest {

    @Test
    void fetchCurrentStack() {
        StackTraceElement currentSte1 = StackFetcher.fetchCurrentStack(); // same to fetchStack(0)
        StackTraceElement currentSte2 = StackFetcher.fetchStack(0); // 0 is offset
        System.out.println(currentSte1);
        System.out.println(currentSte2);
        System.out.println();

        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        for (int i = 0; i < steArray.length; i++) {
            StackTraceElement ste = steArray[i];
            System.out.println(String.format("%4d", i) + " " + ste);
        }

        AssertionsEx.assertThat(currentSte1.getClassName()).isEqualTo(currentSte2.getClassName());
        AssertionsEx.assertThat(currentSte1.getMethodName()).isEqualTo(currentSte2.getMethodName());

        AssertionsEx.assertThat(steArray[1].getClassName()).isEqualTo(currentSte1.getClassName());
        AssertionsEx.assertThat(steArray[1].getMethodName()).isEqualTo(currentSte1.getMethodName());

        AssertionsEx.awareThrown(() -> StackFetcher.fetchStack(-1))
                .hasMessageContaining("offset")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fetchPreviousStack() {
        StackTraceElement ste = testStackPrevious();
        System.out.println(ste);

        AssertionsEx.assertThat(ste.getClassName()).isEqualTo(StackFetcherTest.class.getName());
        AssertionsEx.assertThat(ste.getMethodName()).isEqualTo("fetchPreviousStack");
    }

    @Test
    void fetchStacksPretty() {
        printStack1(() -> {
            StringBuilder sb = StackFetcher.fetchStacksPretty();
            System.out.println(sb);
        });
    }

    @Test
    void fetchStacksPretty_prefix() {
        printStack1(() -> {
            StringBuilder sb = StackFetcher.fetchStacksPretty(">> ");
            System.out.println(sb);
        });
    }

    @Test
    void fetchStacksPretty_prefix_suffix() {
        printStack1(() -> {
            StringBuilder sb = StackFetcher.fetchStacksPretty("- ", " |" + System.lineSeparator());
            System.out.println(sb);
        });
    }

    @Test
    void fetchStacksPretty_offset_prefix_suffix() {
        printStack1(() -> {
            StringBuilder sb = StackFetcher.fetchStacksPretty(1, "- ", " |" + System.lineSeparator());
            System.out.println(sb);
        });
    }

    public StackTraceElement testStackPrevious() {
        return StackFetcher.fetchPreviousStack();
    }

    public void printStack1(Runnable action) {
        printStack2(action);
    }

    public void printStack2(Runnable action) {
        printStack3(action);
    }

    public void printStack3(Runnable action) {
        action.run();
    }

}
