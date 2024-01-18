package org.misty.utils;

import org.misty.utils.fi.RunnableEx;

import java.util.concurrent.atomic.AtomicBoolean;

public class OnceRunnable implements Runnable, RunnableEx {

    private final AtomicBoolean executed = new AtomicBoolean(false);

    private final RunnableEx firstRunnable;

    public OnceRunnable(RunnableEx firstRunnable) {
        this.firstRunnable = firstRunnable;
    }

    @Override
    public void handle() throws Exception {
        boolean firstTime = this.executed.compareAndSet(false, true);
        if (firstTime) {
            this.firstRunnable.handle();
        }
    }

    @Override
    public void run() {
        RunnableEx.super.execute();
    }

}
