package org.misty.utils.range;

public interface DoubleRange extends Range {

    double getLower();

    double getUpper();

    boolean inRange(double value);

    boolean outRange(double value);

    double random();

}
