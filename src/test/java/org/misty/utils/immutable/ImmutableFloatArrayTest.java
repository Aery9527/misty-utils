package org.misty.utils.immutable;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class ImmutableFloatArrayTest {

    @Test
    public void length() {
        AssertionsEx.assertThat(ImmutableArray.ofFloat(new float[0]).getLength()).isEqualTo(0);
        AssertionsEx.assertThat(ImmutableArray.ofFloat(1, 2, 3).getLength()).isEqualTo(3);
    }

    @Test
    public void copy() {
        ImmutableFloatArray array = ImmutableArray.ofFloat(1, 2, 3);

        float[] copy = array.copy();
        copy[0] = 0;
        copy[1] = 0;
        copy[2] = 0;
        AssertionsEx.assertThatThrownBy(() -> copy[3] = 0).isInstanceOf(ArrayIndexOutOfBoundsException.class);

        AssertionsEx.assertThat(array.getArray()).containsExactly(1, 2, 3);
    }

    @Test
    public void get() {
        ImmutableFloatArray array = ImmutableArray.ofFloat(1, 2, 3);
        AssertionsEx.assertThat(array.get(0)).isEqualTo(1);
        AssertionsEx.assertThat(array.get(1)).isEqualTo(2);
        AssertionsEx.assertThat(array.get(2)).isEqualTo(3);
        AssertionsEx.assertThatThrownBy(() -> array.get(3)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    public void forEach() {
        ImmutableFloatArray array = ImmutableArray.ofFloat(1, 2, 3);
        StringBuilder builder = new StringBuilder();
        array.forEach(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("1.02.03.0");
    }

    @Test
    public void foreachReverse() {
        ImmutableFloatArray array = ImmutableArray.ofFloat(1, 2, 3);
        StringBuilder builder = new StringBuilder();
        array.foreachReverse(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("3.02.01.0");
    }

    @Test
    public void stream() {
        ImmutableFloatArray array = ImmutableArray.ofFloat(1, 2, 3);
        AssertionsEx.assertThat(array.stream()).containsExactly(1f, 2f, 3f);
    }

}
