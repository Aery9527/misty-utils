package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReentrantLockExTest {

    @Test
    public void lock_fair() {
        Assertions.assertThat(new ReentrantLockEx().isFair()).isTrue();
        Assertions.assertThat(new ReentrantLockEx(false).isFair()).isFalse();
    }

    @Test
    public void lock_runnable() {
        ReentrantLockEx reentrantLockEx = new ReentrantLockEx();

        AtomicBoolean lockState = new AtomicBoolean();
        reentrantLockEx.lock(() -> lockState.set(reentrantLockEx.isLocked()));

        Assertions.assertThat(lockState.get()).isTrue();
    }

    @Test
    public void lock_supplier() {
        ReentrantLockEx reentrantLockEx = new ReentrantLockEx();

        String returnTarget = UUID.randomUUID().toString();

        AtomicBoolean lockState = new AtomicBoolean();
        String returned = reentrantLockEx.lock(() -> {
            lockState.set(reentrantLockEx.isLocked());
            return returnTarget;
        });

        Assertions.assertThat(lockState.get()).isTrue();
        Assertions.assertThat(returned).isEqualTo(returnTarget);
    }
}
