package org.misty.utils.ex.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"RedundantCast", "ConstantValue", "rawtypes", "OptionalAssignedToNull"})
class JudgeTest {

    @Test
    public void test_Object() {
        Assertions.assertThat(Judge.isNullOrEmpty((Object) null)).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) null)).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new JudgeTest())).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) Optional.empty())).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) Optional.empty())).isEqualTo(false);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new short[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new short[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new short[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new short[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new int[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new int[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new int[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new int[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new long[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new long[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new long[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new long[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new float[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new float[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new float[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new float[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new double[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new double[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new double[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new double[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new boolean[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new boolean[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new boolean[]{true})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new boolean[]{true})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new char[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new char[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new char[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new char[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) new byte[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new byte[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) new byte[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) new byte[]{1})).isEqualTo(true);
    }

    @Test
    public void test_String() {
        Assertions.assertThat(Judge.isNullOrEmpty((String) null)).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((String) null)).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty("")).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty("")).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty("123")).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty("123")).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) "")).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) "")).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) "123")).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) "123")).isEqualTo(true);
    }

    @Test
    public void test_Collection() {
        Assertions.assertThat(Judge.isNullOrEmpty((Collection) null)).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Collection) null)).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Collections.emptyList())).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(Collections.emptyList())).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Collections.singleton(""))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(Collections.singleton(""))).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) Collections.emptyList())).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) Collections.emptyList())).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) Collections.singleton(""))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) Collections.singleton(""))).isEqualTo(true);
    }

    @Test
    public void test_Map() {
        Assertions.assertThat(Judge.isNullOrEmpty((Map) null)).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Map) null)).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Collections.emptyMap())).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(Collections.emptyMap())).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Collections.singletonMap("", ""))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(Collections.singletonMap("", ""))).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty((Object) Collections.emptyMap())).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) Collections.emptyMap())).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty((Object) Collections.singletonMap("", ""))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty((Object) Collections.singletonMap("", ""))).isEqualTo(true);
    }

    @Test
    public void test_Array() {
        Assertions.assertThat(Judge.isNullOrEmpty((String[]) null)).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((String[]) null)).isEqualTo(false);

        Assertions.assertThat(Judge.isNullOrEmpty(new String[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new String[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new String[]{""})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new String[]{""})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new short[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new short[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new short[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new short[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new int[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new int[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new int[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new int[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new long[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new long[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new long[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new long[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new float[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new float[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new float[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new float[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new double[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new double[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new double[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new double[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new boolean[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new boolean[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new boolean[]{true})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new boolean[]{true})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new char[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new char[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new char[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new char[]{1})).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(new byte[]{})).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(new byte[]{})).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(new byte[]{1})).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(new byte[]{1})).isEqualTo(true);
    }

    @Test
    public void test_Optional() {
        Assertions.assertThat(Judge.isNullOrEmpty((Optional) null)).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty((Optional) null)).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Optional.empty())).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.empty())).isEqualTo(false);

        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of(""))).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of(""))).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of("1"))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of("1"))).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of(Collections.emptyList()))).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of(Collections.emptyList()))).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of(Collections.singleton("")))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of(Collections.singleton("")))).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of(Collections.emptyMap()))).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of(Collections.emptyMap()))).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of(Collections.singletonMap("", "")))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of(Collections.singletonMap("", "")))).isEqualTo(true);

        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of(new Object[]{}))).isEqualTo(true);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of(new Object[]{}))).isEqualTo(false);
        Assertions.assertThat(Judge.isNullOrEmpty(Optional.of(new Object[]{1}))).isEqualTo(false);
        Assertions.assertThat(Judge.notNullAndEmpty(Optional.of(new Object[]{1}))).isEqualTo(true);
    }

}
