package org.misty.utils.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"RedundantCast", "ConstantValue", "rawtypes", "OptionalAssignedToNull"})
class CheckerTest {

    @Test
    public void test_Object() {
        Assertions.assertThat(Checker.isNullOrEmpty((Object) null)).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) null)).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new CheckerTest())).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) Optional.empty())).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) Optional.empty())).isEqualTo(false);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new short[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new short[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new short[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new short[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new int[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new int[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new int[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new int[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new long[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new long[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new long[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new long[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new float[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new float[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new float[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new float[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new double[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new double[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new double[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new double[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new boolean[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new boolean[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new boolean[]{true})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new boolean[]{true})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new char[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new char[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new char[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new char[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) new byte[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new byte[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) new byte[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) new byte[]{1})).isEqualTo(true);
    }

    @Test
    public void test_String() {
        Assertions.assertThat(Checker.isNullOrEmpty((String) null)).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((String) null)).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty("")).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty("")).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty("123")).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty("123")).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) "")).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) "")).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) "123")).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) "123")).isEqualTo(true);
    }

    @Test
    public void test_Collection() {
        Assertions.assertThat(Checker.isNullOrEmpty((Collection) null)).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Collection) null)).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Collections.emptyList())).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(Collections.emptyList())).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Collections.singleton(""))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(Collections.singleton(""))).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) Collections.emptyList())).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) Collections.emptyList())).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) Collections.singleton(""))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) Collections.singleton(""))).isEqualTo(true);
    }

    @Test
    public void test_Map() {
        Assertions.assertThat(Checker.isNullOrEmpty((Map) null)).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Map) null)).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Collections.emptyMap())).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(Collections.emptyMap())).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Collections.singletonMap("", ""))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(Collections.singletonMap("", ""))).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty((Object) Collections.emptyMap())).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) Collections.emptyMap())).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty((Object) Collections.singletonMap("", ""))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty((Object) Collections.singletonMap("", ""))).isEqualTo(true);
    }

    @Test
    public void test_Array() {
        Assertions.assertThat(Checker.isNullOrEmpty((String[]) null)).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((String[]) null)).isEqualTo(false);

        Assertions.assertThat(Checker.isNullOrEmpty(new String[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new String[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new String[]{""})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new String[]{""})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new short[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new short[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new short[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new short[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new int[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new int[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new int[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new int[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new long[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new long[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new long[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new long[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new float[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new float[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new float[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new float[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new double[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new double[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new double[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new double[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new boolean[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new boolean[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new boolean[]{true})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new boolean[]{true})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new char[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new char[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new char[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new char[]{1})).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(new byte[]{})).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(new byte[]{})).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(new byte[]{1})).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(new byte[]{1})).isEqualTo(true);
    }

    @Test
    public void test_Optional() {
        Assertions.assertThat(Checker.isNullOrEmpty((Optional) null)).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty((Optional) null)).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Optional.empty())).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.empty())).isEqualTo(false);

        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of(""))).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of(""))).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of("1"))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of("1"))).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of(Collections.emptyList()))).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of(Collections.emptyList()))).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of(Collections.singleton("")))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of(Collections.singleton("")))).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of(Collections.emptyMap()))).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of(Collections.emptyMap()))).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of(Collections.singletonMap("", "")))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of(Collections.singletonMap("", "")))).isEqualTo(true);

        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of(new Object[]{}))).isEqualTo(true);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of(new Object[]{}))).isEqualTo(false);
        Assertions.assertThat(Checker.isNullOrEmpty(Optional.of(new Object[]{1}))).isEqualTo(false);
        Assertions.assertThat(Checker.notNullAndEmpty(Optional.of(new Object[]{1}))).isEqualTo(true);
    }

}
