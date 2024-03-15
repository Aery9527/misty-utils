package org.misty.utils.immutable;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class ImmutableObjectArrayTest {

    @Test
    public void length() {
        AssertionsEx.assertThat(ImmutableArray.of(new String[0]).getLength()).isEqualTo(0);
        AssertionsEx.assertThat(ImmutableArray.of("a", "b", "c").getLength()).isEqualTo(3);
    }

    @Test
    public void copy() {
        ImmutableObjectArray<String> array = ImmutableArray.of("a", "b", "c");

        String[] copy = array.copy();
        copy[0] = "";
        copy[1] = "";
        copy[2] = "";
        AssertionsEx.assertThatThrownBy(() -> copy[3] = "").isInstanceOf(ArrayIndexOutOfBoundsException.class);

        AssertionsEx.assertThat(array.getArray()).containsExactly("a", "b", "c");
    }

    @Test
    public void get() {
        ImmutableObjectArray<String> array = ImmutableArray.of("a", "b", "c");
        AssertionsEx.assertThat(array.get(0)).isEqualTo("a");
        AssertionsEx.assertThat(array.get(1)).isEqualTo("b");
        AssertionsEx.assertThat(array.get(2)).isEqualTo("c");
        AssertionsEx.assertThatThrownBy(() -> array.get(3)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    public void forEach() {
        ImmutableObjectArray<String> array = ImmutableArray.of("a", "b", "c");
        StringBuilder builder = new StringBuilder();
        array.forEach(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("abc");
    }

    @Test
    public void foreachReverse() {
        ImmutableObjectArray<String> array = ImmutableArray.of("a", "b", "c");
        StringBuilder builder = new StringBuilder();
        array.foreachReverse(builder::append);
        AssertionsEx.assertThat(builder.toString()).isEqualTo("cba");
    }

    @Test
    public void stream() {
        ImmutableObjectArray<String> array = ImmutableArray.of("a", "b", "c");
        AssertionsEx.assertThat(array.stream()).containsExactly("a", "b", "c");
    }

}
