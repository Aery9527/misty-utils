package org.misty.utils.limit;

import java.math.BigDecimal;

public interface BigDecimalLimiter extends Limiter {

    BigDecimal get();

    void set(BigDecimal value);

    BigDecimal plus(BigDecimal plus);

    BigDecimal minus(BigDecimal minus);

    default BigDecimal increment() {
        return plus(BigDecimal.ONE);
    }

    default BigDecimal decrement() {
        return minus(BigDecimal.ONE);
    }

}
