package org.misty.utils.immutable;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class ImmutableShortArrayTest {

    @Test
    public void length() {
        AssertionsEx.assertThat(ImmutableArray.ofShort(new short[0]).getLength()).isEqualTo(0);
        AssertionsEx.assertThat(ImmutableArray.ofShort((short) 1, (short) 2, (short) 3).getLength()).isEqualTo(3);
    }

    @Test
    public void copy() {
        ImmutableShortArray array = ImmutableArray.ofShort((short) 1, (short) 2, (short) 3);

        short[] copy = array.copy();
        copy[0] = 0;
        copy[1] = 0;
        copy[2] = 0;
        AssertionsEx.assertThatThrownBy(() -> copy[3] = 0).isInstanceOf(ArrayIndexOutOfBoundsException.class);

        AssertionsEx.assertThat(array.getArray()).containsExactly((short) 1, (short) 2, (short) 3);
    }

    @Test
    public void get() {
        ImmutableShortArray array = ImmutableArray.ofShort((short) 1, (short) 2, (short) 3);
        AssertionsEx.assertThat(array.get(0)).isEqualTo((short) 1);
        AssertionsEx.assertThat(array.get(1)).isEqualTo((short) 2);
        AssertionsEx.assertThat(array.get(2)).isEqualTo((short) 3);
        AssertionsEx.assertThatThrownBy(() -> array.get(3)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    public void forEach() {
        ImmutableShortArray array = ImmutableArray.ofShort((short) 1, (short) 2, (short) 3);
        StringBuilder builder = new StringBuilder();
        array.forEach(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("123");
    }

    @Test
    public void foreachReverse() {
        ImmutableShortArray array = ImmutableArray.ofShort((short) 1, (short) 2, (short) 3);
        StringBuilder builder = new StringBuilder();
        array.foreachReverse(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("321");
    }

    @Test
    public void stream() {
        ImmutableShortArray array = ImmutableArray.ofShort((short) 1, (short) 2, (short) 3);
        AssertionsEx.assertThat(array.stream()).containsExactly((short) 1, (short) 2, (short) 3);
    }

}
