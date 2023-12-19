package org.misty.utils.range;

public interface IntRange extends Range {

    int getLower();

    int getUpper();

    boolean inRange(int value);

    boolean outRange(int value);

    int random();

}
