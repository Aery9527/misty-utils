package org.misty.utils.range;

public interface FloatRange extends Range {

    float getLower();

    float getUpper();

    boolean inRange(float value);

    boolean outRange(float value);

    float random();

}
