package org.misty.utils.cycle;

public interface LongCycle extends Cycle {

    long get();

    long getStep();

    long getMin();

    long getMax();

    long getAndNext();

    long nextAndGet();

}
