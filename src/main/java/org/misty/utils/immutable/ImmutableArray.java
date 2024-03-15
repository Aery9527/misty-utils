package org.misty.utils.immutable;

import org.misty.utils.OnceSupplier;

import java.lang.reflect.Array;

public abstract class ImmutableArray<ArrayType> {

    public static <Type> ImmutableObjectArray<Type> of(Type... array) {
        return new ImmutableObjectArray<>(array);
    }

    public static ImmutableShortArray ofShort(short... array) {
        return new ImmutableShortArray(array);
    }

    public static ImmutableIntArray ofInt(int... array) {
        return new ImmutableIntArray(array);
    }

    public static ImmutableLongArray ofLong(long... array) {
        return new ImmutableLongArray(array);
    }

    public static ImmutableDoubleArray ofDouble(double... array) {
        return new ImmutableDoubleArray(array);
    }

    public static ImmutableFloatArray ofFloat(float... array) {
        return new ImmutableFloatArray(array);
    }

    private final ArrayType array;

    private final OnceSupplier<Integer> length;

    protected ImmutableArray(ArrayType array) {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("The argument must be an array");
        }

        this.array = array;
        this.length = new OnceSupplier<>(() -> Array.getLength(array));
    }

    public int getLength() {
        return this.length.get();
    }

    protected ArrayType getArray() {
        return array;
    }

    public abstract ArrayType copy();

}
