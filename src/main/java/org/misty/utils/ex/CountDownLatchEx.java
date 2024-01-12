package org.misty.utils.ex;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class CountDownLatchEx extends CountDownLatch {

    public CountDownLatchEx(int count) {
        super(count);
    }

    public boolean wating() {
        return wating(e -> {
        });
    }

    public boolean wating(Consumer<InterruptedException> interruptHandler) {
        try {
            super.await();
            return false;
        } catch (InterruptedException e) {
            interruptHandler.accept(e);
            return true;
        }
    }

}
