package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

class ReentrantLockExTest {

    @Test
    void use_fair() {
        Assertions.assertThat(new ReentrantLockEx().isFair()).isTrue();
        Assertions.assertThat(new ReentrantLockEx(false).isFair()).isFalse();
    }

    @Test
    void use_runnable() {
        ReentrantLockEx reentrantLockEx = new ReentrantLockEx();

        AtomicBoolean lockState = new AtomicBoolean();
        reentrantLockEx.use(() -> lockState.set(reentrantLockEx.isLocked()));

        Assertions.assertThat(lockState.get()).isTrue();
    }

    @Test
    void use_supplier() {
        ReentrantLockEx reentrantLockEx = new ReentrantLockEx();

        String returnTarget = UUID.randomUUID().toString();

        AtomicBoolean lockState = new AtomicBoolean();
        String returned = reentrantLockEx.use(() -> {
            lockState.set(reentrantLockEx.isLocked());
            return returnTarget;
        });

        Assertions.assertThat(lockState.get()).isTrue();
        Assertions.assertThat(returned).isEqualTo(returnTarget);
    }
}
