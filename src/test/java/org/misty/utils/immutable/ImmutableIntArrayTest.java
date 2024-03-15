package org.misty.utils.immutable;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class ImmutableIntArrayTest {

    @Test
    public void length() {
        AssertionsEx.assertThat(ImmutableArray.ofInt(new int[0]).getLength()).isEqualTo(0);
        AssertionsEx.assertThat(ImmutableArray.ofInt(1, 2, 3).getLength()).isEqualTo(3);
    }

    @Test
    public void copy() {
        ImmutableIntArray array = ImmutableArray.ofInt(1, 2, 3);

        int[] copy = array.copy();
        copy[0] = 0;
        copy[1] = 0;
        copy[2] = 0;
        AssertionsEx.assertThatThrownBy(() -> copy[3] = 0).isInstanceOf(ArrayIndexOutOfBoundsException.class);

        AssertionsEx.assertThat(array.getArray()).containsExactly(1, 2, 3);
    }

    @Test
    public void get() {
        ImmutableIntArray array = ImmutableArray.ofInt(1, 2, 3);
        AssertionsEx.assertThat(array.get(0)).isEqualTo(1);
        AssertionsEx.assertThat(array.get(1)).isEqualTo(2);
        AssertionsEx.assertThat(array.get(2)).isEqualTo(3);
        AssertionsEx.assertThatThrownBy(() -> array.get(3)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    public void forEach() {
        ImmutableIntArray array = ImmutableArray.ofInt(1, 2, 3);
        StringBuilder builder = new StringBuilder();
        array.forEach(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("123");
    }

    @Test
    public void foreachReverse() {
        ImmutableIntArray array = ImmutableArray.ofInt(1, 2, 3);
        StringBuilder builder = new StringBuilder();
        array.foreachReverse(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("321");
    }

    @Test
    public void stream() {
        ImmutableIntArray array = ImmutableArray.ofInt(1, 2, 3);
        AssertionsEx.assertThat(array.stream()).containsExactly(1, 2, 3);
    }

}
