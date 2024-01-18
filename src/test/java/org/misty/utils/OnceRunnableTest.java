package org.misty.utils;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.util.concurrent.atomic.AtomicInteger;

public class OnceRunnableTest {

    @Test
    public void once_execute() {
        AtomicInteger check = new AtomicInteger(0);
        OnceRunnable once = new OnceRunnable(check::incrementAndGet);

        once.execute();
        once.execute();
        once.run();
        once.run();

        AssertionsEx.assertThat(check.get()).isEqualTo(1);
    }

}
