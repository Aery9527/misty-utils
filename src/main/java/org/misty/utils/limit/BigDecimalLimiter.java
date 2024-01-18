package org.misty.utils.limit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public interface BigDecimalLimiter extends Limiter {

    int DEFAULT_SCALE = 2;

    RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    BigDecimal get();

    void set(BigDecimal value);

    BigDecimal plus(BigDecimal plus);

    BigDecimal minus(BigDecimal minus);

    Optional<BigDecimal> getMin();

    Optional<BigDecimal> getMax();

    default BigDecimal increment() {
        return plus(BigDecimal.ONE);
    }

    default BigDecimal decrement() {
        return minus(BigDecimal.ONE);
    }

}
