package org.misty.utils.cycle;

public interface ShortCycle extends Cycle {

    short get();

    short getStep();

    short getMin();

    short getMax();

    short getAndNext();

    short nextAndGet();

}
