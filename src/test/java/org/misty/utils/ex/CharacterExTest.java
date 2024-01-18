package org.misty.utils.ex;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharacterExTest {

    @Test
    public void countChinese() {
        Assertions.assertThat(CharacterEx.countChinese("abc")).isEqualTo(0);
        Assertions.assertThat(CharacterEx.countChinese("一二三")).isEqualTo(3);
        Assertions.assertThat(CharacterEx.countChinese("a一b二c三d")).isEqualTo(3);
    }

}
