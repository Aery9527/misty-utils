package org.misty.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class IntFlagTest {

    @Test
    public void add_addWithDecimal_has() {
        IntFlag intFlag = IntFlag.create(1);
        intFlag.add(4);

        Assertions.assertThat(intFlag.has(1)).isTrue();
        Assertions.assertThat(intFlag.has(2)).isFalse();
        Assertions.assertThat(intFlag.has(4)).isTrue();
        Assertions.assertThat(intFlag.has(8)).isFalse();
    }

    @Test
    public void remove_has() {
        IntFlag intFlag = IntFlag.create(1 | 2 | 4 | 8);
        intFlag.remove(2);
        intFlag.remove(8);

        Assertions.assertThat(intFlag.has(1)).isTrue();
        Assertions.assertThat(intFlag.has(2)).isFalse();
        Assertions.assertThat(intFlag.has(4)).isTrue();
        Assertions.assertThat(intFlag.has(8)).isFalse();
    }

    @Test
    public void tip() {
        IntFlag intFlag = IntFlag.create(1 | 4);
        intFlag.tip();

        Assertions.assertThat(intFlag.has(1)).isFalse();
        Assertions.assertThat(intFlag.has(2)).isTrue();
        Assertions.assertThat(intFlag.has(4)).isFalse();
        Assertions.assertThat(intFlag.has(8)).isTrue();
    }

}
