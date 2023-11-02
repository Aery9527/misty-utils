package org.misty.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BitFlagTest {

    @Test
    public void add_addWithDecimal_has() {
        BitFlag bitFlag = BitFlag.create(1);
        bitFlag.add(4);

        Assertions.assertThat(bitFlag.has(1)).isTrue();
        Assertions.assertThat(bitFlag.has(2)).isFalse();
        Assertions.assertThat(bitFlag.has(4)).isTrue();
        Assertions.assertThat(bitFlag.has(8)).isFalse();
    }

    @Test
    public void remove_has() {
        BitFlag bitFlag = BitFlag.create(1 | 2 | 4 | 8);
        bitFlag.remove(2);
        bitFlag.remove(8);

        Assertions.assertThat(bitFlag.has(1)).isTrue();
        Assertions.assertThat(bitFlag.has(2)).isFalse();
        Assertions.assertThat(bitFlag.has(4)).isTrue();
        Assertions.assertThat(bitFlag.has(8)).isFalse();
    }

    @Test
    public void tip() {
        BitFlag bitFlag = BitFlag.create(1 | 4);
        bitFlag.tip();

        Assertions.assertThat(bitFlag.has(1)).isFalse();
        Assertions.assertThat(bitFlag.has(2)).isTrue();
        Assertions.assertThat(bitFlag.has(4)).isFalse();
        Assertions.assertThat(bitFlag.has(8)).isTrue();
    }

}
