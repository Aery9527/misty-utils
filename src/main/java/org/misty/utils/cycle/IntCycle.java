package org.misty.utils.cycle;

public interface IntCycle extends Cycle {

    int get();

    int getStep();

    int getMin();

    int getMax();

    int getAndNext();

    int nextAndGet();

}
