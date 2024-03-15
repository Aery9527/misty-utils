package org.misty.utils.immutable;

import org.misty.utils.fi.ShortConsumerEx;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ImmutableShortArray extends ImmutableArray<short[]> {

    public ImmutableShortArray(short[] array) {
        super(array);
    }

    @Override
    public short[] copy() {
        return getArray().clone();
    }

    public short get(int index) {
        return getArray()[index];
    }

    public void forEach(ShortConsumerEx consumer) {
        for (short element : getArray()) {
            consumer.execute(element);
        }
    }

    public void foreachReverse(ShortConsumerEx consumer) {
        for (int i = getLength() - 1; i >= 0; i--) {
            consumer.execute(get(i));
        }
    }

    public Stream<Short> stream() {
        return IntStream.range(0, getLength()).mapToObj(this::get);
    }

}
