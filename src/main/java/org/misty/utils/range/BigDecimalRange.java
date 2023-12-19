package org.misty.utils.range;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface BigDecimalRange extends Range {

    BigDecimal getLower();

    BigDecimal getUpper();

    boolean inRange(BigDecimal value);

    boolean outRange(BigDecimal value);

    BigDecimal random();

    int getScale();

    RoundingMode getRoundingMode();

}
