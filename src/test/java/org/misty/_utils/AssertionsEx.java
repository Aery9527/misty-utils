package org.misty._utils;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.misty.utils.StackFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class AssertionsEx extends Assertions {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssertionsEx.class);

    public static AbstractThrowableAssert<?, ? extends Throwable> assertThrown(ThrowableAssert.ThrowingCallable throwingCallable) {
        return assertThrown(throwingCallable, true);
    }

    public static AbstractThrowableAssert<?, ? extends Throwable> assertThrown(ThrowableAssert.ThrowingCallable throwingCallable, boolean onlyPreviousStack) {
        StackTraceElement ste = StackFetcher.fetchStack(2);
        Consumer<Exception> printStackAction = onlyPreviousStack ? e -> { // print only caller stack
            System.out.println("ERROR MESSAGE : {" + System.lineSeparator()
                    + e.getMessage() + System.lineSeparator()
                    + "} at " + ste.toString());
        } : e -> { // print all stack
            LOGGER.error("ERROR MESSAGE", e);
        };

        return Assertions.assertThatThrownBy(() -> {
            try {
                throwingCallable.call();
            } catch (Exception e) {
                printStackAction.accept(e);
                throw e;
            }
        });
    }

}
