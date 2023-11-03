package org.misty.utils.flag;

import java.util.Collection;

public interface BitFlag {

    static IntFlag useInt() {
        return IntFlag.create();
    }

    static IntFlag useInt(int flags) {
        return IntFlag.create(flags);
    }

    static LongFlag useLong() {
        return LongFlag.create();
    }

    static LongFlag useLong(long flags) {
        return LongFlag.create(flags);
    }

    static DecimalFlag useDecimal() {
        return DecimalFlag.create();
    }

    static DecimalFlag useDecimal(int number) {
        return DecimalFlag.create(number);
    }

    static DecimalFlag useDecimal(int... numbers) {
        return DecimalFlag.create(numbers);
    }

    static DecimalFlag useDecimal(Collection<Integer> numbers) {
        return DecimalFlag.create(numbers);
    }

}
