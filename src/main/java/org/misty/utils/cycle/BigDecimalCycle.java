package org.misty.utils.cycle;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface BigDecimalCycle extends Cycle {

    int DEFAULT_SCALE = 2;

    RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    default float getToFloat() {
        return get().floatValue();
    }

    default double getToDouble() {
        return get().doubleValue();
    }

    BigDecimal get();

    default float getStepToFloat() {
        return getStep().floatValue();
    }

    default double getStepToDouble() {
        return getStep().doubleValue();
    }

    BigDecimal getStep();

    default float getMinToFloat() {
        return getMin().floatValue();
    }

    default double getMinToDouble() {
        return getMin().doubleValue();
    }

    BigDecimal getMin();

    default float getMaxToFloat() {
        return getMax().floatValue();
    }

    default double getMaxToDouble() {
        return getMax().doubleValue();
    }

    BigDecimal getMax();

    default float getAndNextToFloat() {
        return getAndNext().floatValue();
    }

    default double getAndNextToDouble() {
        return getAndNext().doubleValue();
    }

    BigDecimal getAndNext();

    default float nextAndGetToFloat() {
        return nextAndGet().floatValue();
    }

    default double nextAndGetToDouble() {
        return nextAndGet().doubleValue();
    }

    BigDecimal nextAndGet();

}
