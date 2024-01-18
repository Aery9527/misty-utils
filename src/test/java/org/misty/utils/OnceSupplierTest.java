package org.misty.utils;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.concurrent.atomic.AtomicInteger;

public class OnceSupplierTest {

    @Test
    public void once_execute() {
        AtomicInteger check = new AtomicInteger(0);
        OnceSupplier<String> once = new OnceSupplier<>(() -> {
            check.incrementAndGet();
            return "test";
        });

        AssertionsEx.assertThat(once.execute()).isEqualTo("test");
        AssertionsEx.assertThat(once.execute()).isEqualTo("test");
        AssertionsEx.assertThat(once.get()).isEqualTo("test");
        AssertionsEx.assertThat(once.get()).isEqualTo("test");

        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

}
