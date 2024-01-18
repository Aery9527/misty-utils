package org.misty.utils.ex;

import org.misty.utils.fi.RunnableEx;
import org.misty.utils.fi.SupplierEx;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockEx extends ReentrantLock {

    public ReentrantLockEx() {
        this(true);
    }

    public ReentrantLockEx(boolean fair) {
        super(fair);
    }

    public void lock(RunnableEx action) {
        try {
            super.lock();
            action.execute();
        } finally {
            super.unlock();
        }
    }

    public <ReturnType> ReturnType lock(SupplierEx<ReturnType> action) {
        try {
            super.lock();
            return action.execute();
        } finally {
            super.unlock();
        }
    }

}
