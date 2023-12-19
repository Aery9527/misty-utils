package org.misty.utils.range;

public interface ShortRange extends Range {

    short getLower();

    short getUpper();

    boolean inRange(short value);

    boolean outRange(short value);

    short random();

}
