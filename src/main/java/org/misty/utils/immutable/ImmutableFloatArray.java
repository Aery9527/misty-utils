package org.misty.utils.immutable;

import org.misty.utils.fi.FloatConsumerEx;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ImmutableFloatArray extends ImmutableArray<float[]> {

    public ImmutableFloatArray(float[] array) {
        super(array);
    }

    @Override
    public float[] copy() {
        return getArray().clone();
    }

    public float get(int index) {
        return getArray()[index];
    }

    public void forEach(FloatConsumerEx consumer) {
        for (float element : getArray()) {
            consumer.execute(element);
        }
    }

    public void foreachReverse(FloatConsumerEx consumer) {
        for (int i = getLength() - 1; i >= 0; i--) {
            consumer.execute(get(i));
        }
    }

    public Stream<Float> stream() {
        return IntStream.range(0, getLength()).mapToObj(this::get);
    }

}
