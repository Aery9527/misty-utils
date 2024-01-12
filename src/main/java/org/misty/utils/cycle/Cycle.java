package org.misty.utils.cycle;

import org.misty.utils.Tracked;

public interface Cycle {

    static ShortCycleBuilder shortCycleBuilder() {
        return shortCycleBuilder(ShortCycle.class);
    }

    static ShortCycleBuilder shortCycleBuilder(String name) {
        return shortCycleBuilder(Tracked.create(name));
    }

    static ShortCycleBuilder shortCycleBuilder(Class<?> clazz) {
        return shortCycleBuilder(Tracked.create(clazz));
    }

    static IntCycleBuilder intCycleBuilder() {
        return intCycleBuilder(IntCycle.class);
    }

    static IntCycleBuilder intCycleBuilder(String name) {
        return intCycleBuilder(Tracked.create(name));
    }

    static IntCycleBuilder intCycleBuilder(Class<?> clazz) {
        return intCycleBuilder(Tracked.create(clazz));
    }

    static IntCycleBuilder intCycleBuilder(Tracked tracked) {
        return new IntCycleBuilder(tracked);
    }

    static ShortCycleBuilder shortCycleBuilder(Tracked tracked) {
        return new ShortCycleBuilder(tracked);
    }

    static LongCycleBuilder longCycleBuilder() {
        return longCycleBuilder(LongCycle.class);
    }

    static LongCycleBuilder longCycleBuilder(String name) {
        return longCycleBuilder(Tracked.create(name));
    }

    static LongCycleBuilder longCycleBuilder(Class<?> clazz) {
        return longCycleBuilder(Tracked.create(clazz));
    }

    static LongCycleBuilder longCycleBuilder(Tracked tracked) {
        return new LongCycleBuilder(tracked);
    }

    static BigDecimalCycleBuilder bigDecimalCycleBuilder() {
        return bigDecimalCycleBuilder(BigDecimalCycle.class);
    }

    static BigDecimalCycleBuilder bigDecimalCycleBuilder(String name) {
        return bigDecimalCycleBuilder(Tracked.create(name));
    }

    static BigDecimalCycleBuilder bigDecimalCycleBuilder(Class<?> clazz) {
        return bigDecimalCycleBuilder(Tracked.create(clazz));
    }

    static BigDecimalCycleBuilder bigDecimalCycleBuilder(Tracked tracked) {
        return new BigDecimalCycleBuilder(tracked);
    }

    static BigIntegerCycleBuilder bigIntegerCycleBuilder() {
        return bigIntegerCycleBuilder(BigIntegerCycle.class);
    }

    static BigIntegerCycleBuilder bigIntegerCycleBuilder(String name) {
        return bigIntegerCycleBuilder(Tracked.create(name));
    }

    static BigIntegerCycleBuilder bigIntegerCycleBuilder(Class<?> clazz) {
        return bigIntegerCycleBuilder(Tracked.create(clazz));
    }

    static BigIntegerCycleBuilder bigIntegerCycleBuilder(Tracked tracked) {
        return new BigIntegerCycleBuilder(tracked);
    }

    Tracked getTracked();

}
