package org.misty.utils.immutable;

import org.misty.utils.fi.ConsumerEx;

import java.util.stream.Stream;

public class ImmutableObjectArray<Type> extends ImmutableArray<Type[]> {

    public ImmutableObjectArray(Type[] array) {
        super(array);
    }

    @Override
    public Type[] copy() {
        return getArray().clone();
    }

    public Type get(int index) {
        return getArray()[index];
    }

    public void forEach(ConsumerEx<Type> consumer) {
        for (Type element : getArray()) {
            consumer.execute(element);
        }
    }

    public void foreachReverse(ConsumerEx<Type> consumer) {
        for (int i = getLength() - 1; i >= 0; i--) {
            consumer.execute(get(i));
        }
    }

    public Stream<Type> stream() {
        return Stream.of(getArray());
    }

}
