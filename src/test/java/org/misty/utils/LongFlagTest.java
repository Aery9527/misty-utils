package org.misty.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LongFlagTest {

    @Test
    public void add_addWithDecimal_has() {
        LongFlag longFlag = LongFlag.create(1);
        longFlag.add(4);

        Assertions.assertThat(longFlag.has(1)).isTrue();
        Assertions.assertThat(longFlag.has(2)).isFalse();
        Assertions.assertThat(longFlag.has(4)).isTrue();
        Assertions.assertThat(longFlag.has(8)).isFalse();
    }

    @Test
    public void remove_has() {
        LongFlag longFlag = LongFlag.create(1 | 2 | 4 | 8);
        longFlag.remove(2);
        longFlag.remove(8);

        Assertions.assertThat(longFlag.has(1)).isTrue();
        Assertions.assertThat(longFlag.has(2)).isFalse();
        Assertions.assertThat(longFlag.has(4)).isTrue();
        Assertions.assertThat(longFlag.has(8)).isFalse();
    }

    @Test
    public void tip() {
        LongFlag longFlag = LongFlag.create(1 | 4);
        longFlag.tip();

        Assertions.assertThat(longFlag.has(1)).isFalse();
        Assertions.assertThat(longFlag.has(2)).isTrue();
        Assertions.assertThat(longFlag.has(4)).isFalse();
        Assertions.assertThat(longFlag.has(8)).isTrue();
    }

}
