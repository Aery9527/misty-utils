package org.misty.utils.immutable;

import org.misty.utils.fi.LongConsumerEx;

import java.util.stream.LongStream;

public class ImmutableLongArray extends ImmutableArray<long[]> {

    public ImmutableLongArray(long[] array) {
        super(array);
    }

    @Override
    public long[] copy() {
        return getArray().clone();
    }

    public long get(int index) {
        return getArray()[index];
    }

    public void forEach(LongConsumerEx consumer) {
        for (long element : getArray()) {
            consumer.execute(element);
        }
    }

    public void foreachReverse(LongConsumerEx consumer) {
        for (int i = getLength() - 1; i >= 0; i--) {
            consumer.execute(get(i));
        }
    }

    public LongStream stream() {
        return LongStream.of(getArray());
    }

}
