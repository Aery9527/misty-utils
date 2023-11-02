package org.misty._utils;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssertionsEx extends Assertions {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssertionsEx.class);

    public static AbstractThrowableAssert<?, ? extends Throwable> assertThrown(ThrowableAssert.ThrowingCallable throwingCallable) {
        return Assertions.assertThatThrownBy(() -> {
            try {
                throwingCallable.call();
            } catch (Exception e) {
                LOGGER.error("", e);
                throw e;
            }
        });
    }

}
