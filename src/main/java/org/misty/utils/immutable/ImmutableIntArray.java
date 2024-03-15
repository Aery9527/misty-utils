package org.misty.utils.immutable;

import org.misty.utils.fi.IntConsumerEx;

import java.util.stream.IntStream;

public class ImmutableIntArray extends ImmutableArray<int[]> {

    public ImmutableIntArray(int[] array) {
        super(array);
    }

    @Override
    public int[] copy() {
        return getArray().clone();
    }

    public int get(int index) {
        return getArray()[index];
    }

    public void forEach(IntConsumerEx consumer) {
        for (int element : getArray()) {
            consumer.execute(element);
        }
    }

    public void foreachReverse(IntConsumerEx consumer) {
        for (int i = getLength() - 1; i >= 0; i--) {
            consumer.execute(get(i));
        }
    }

    public IntStream stream() {
        return IntStream.of(getArray());
    }

}
