package org.misty.utils.immutable;

import org.misty.utils.fi.DoubleConsumerEx;

import java.util.stream.DoubleStream;

public class ImmutableDoubleArray extends ImmutableArray<double[]> {

    public ImmutableDoubleArray(double[] array) {
        super(array);
    }

    @Override
    public double[] copy() {
        return getArray().clone();
    }

    public double get(int index) {
        return getArray()[index];
    }

    public void forEach(DoubleConsumerEx consumer) {
        for (double element : getArray()) {
            consumer.execute(element);
        }
    }

    public void foreachReverse(DoubleConsumerEx consumer) {
        for (int i = getLength() - 1; i >= 0; i--) {
            consumer.execute(get(i));
        }
    }

    public DoubleStream stream() {
        return DoubleStream.of(getArray());
    }

}
