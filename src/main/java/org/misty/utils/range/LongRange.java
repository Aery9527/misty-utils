package org.misty.utils.range;

public interface LongRange extends Range {

    long getLower();

    long getUpper();

    boolean inRange(long value);

    boolean outRange(long value);

    long random();

}
