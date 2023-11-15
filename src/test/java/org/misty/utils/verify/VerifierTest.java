package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

public class VerifierTest {

    public static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }

    public static <TargetType> VerifierThrown<TargetType, TestException> thrown() {
        return (term, arg, errorMsg) -> {
            throw new TestException(errorMsg);
        };
    }

    @Test
    public void refuseNullOrEmpty() {
        // 測試檢查通過的情況
        Verifier.refuseNullOrEmpty("term", new Object());
        Verifier.refuseNullOrEmpty("term", "123");
        Verifier.refuseNullOrEmpty("term", Collections.singletonList("A"));
        Verifier.refuseNullOrEmpty("term", Collections.singletonMap("A", "B"));
        Verifier.refuseNullOrEmpty("term", new short[]{1});
        Verifier.refuseNullOrEmpty("term", new int[]{1});
        Verifier.refuseNullOrEmpty("term", new long[]{1});
        Verifier.refuseNullOrEmpty("term", new float[]{1});
        Verifier.refuseNullOrEmpty("term", new double[]{1});
        Verifier.refuseNullOrEmpty("term", new boolean[]{true});
        Verifier.refuseNullOrEmpty("term", new char[]{' '});
        Verifier.refuseNullOrEmpty("term", new byte[]{1});
        Verifier.refuseNullOrEmpty("term", new Object[]{new Object()});
        Verifier.refuseNullOrEmpty("term", Optional.of("A"));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", null)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", "")).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Collections.emptyList())).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Collections.emptyMap())).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new short[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new int[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new long[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new float[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new double[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new boolean[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new char[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new byte[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new Object[0])).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Optional.ofNullable(null))).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.refuseNullOrEmpty("term", new Object(), thrown());
        Verifier.refuseNullOrEmpty("term", "123", thrown());
        Verifier.refuseNullOrEmpty("term", Collections.singletonList("A"), thrown());
        Verifier.refuseNullOrEmpty("term", Collections.singletonMap("A", "B"), thrown());
        Verifier.refuseNullOrEmpty("term", new short[]{1}, thrown());
        Verifier.refuseNullOrEmpty("term", new int[]{1}, thrown());
        Verifier.refuseNullOrEmpty("term", new long[]{1}, thrown());
        Verifier.refuseNullOrEmpty("term", new float[]{1}, thrown());
        Verifier.refuseNullOrEmpty("term", new double[]{1}, thrown());
        Verifier.refuseNullOrEmpty("term", new boolean[]{true}, thrown());
        Verifier.refuseNullOrEmpty("term", new char[]{' '}, thrown());
        Verifier.refuseNullOrEmpty("term", new byte[]{1}, thrown());
        Verifier.refuseNullOrEmpty("term", new Object[]{new Object()}, thrown());
        Verifier.refuseNullOrEmpty("term", Optional.of("A"), thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", null, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", "", thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Collections.emptyList(), thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Collections.emptyMap(), thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new short[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new int[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new long[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new float[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new double[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new boolean[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new char[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new byte[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new Object[0], thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Optional.ofNullable(null), thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void short_requireMoreThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireShortMoreThanInclusive("term", (short) 1, (short) 0);
        Verifier.requireShortMoreThanInclusive("term", (short) 1, (short) 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive("term", (short) 1, (short) 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortMoreThanInclusive("term", (short) 1, (short) 0, thrown());
        Verifier.requireShortMoreThanInclusive("term", (short) 1, (short) 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive("term", (short) 1, (short) 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void short_requireMoreThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireShortMoreThanExclusive("term", (short) 1, (short) 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive("term", (short) 1, (short) 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive("term", (short) 1, (short) 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortMoreThanExclusive("term", (short) 1, (short) 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive("term", (short) 1, (short) 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive("term", (short) 1, (short) 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void short_requireLessThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireShortLessThanInclusive("term", (short) 1, (short) 2);
        Verifier.requireShortLessThanInclusive("term", (short) 1, (short) 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive("term", (short) 1, (short) 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortLessThanInclusive("term", (short) 1, (short) 2, thrown());
        Verifier.requireShortLessThanInclusive("term", (short) 1, (short) 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive("term", (short) 1, (short) 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void short_requireLessThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireShortLessThanExclusive("term", (short) 1, (short) 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive("term", (short) 1, (short) 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive("term", (short) 1, (short) 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortLessThanExclusive("term", (short) 1, (short) 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive("term", (short) 1, (short) 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive("term", (short) 1, (short) 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireMoreThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireIntMoreThanInclusive("term", 1, 0);
        Verifier.requireIntMoreThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntMoreThanInclusive("term", 1, 0, thrown());
        Verifier.requireIntMoreThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireMoreThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireIntMoreThanExclusive("term", 1, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntMoreThanExclusive("term", 1, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireLessThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireIntLessThanInclusive("term", 1, 2);
        Verifier.requireIntLessThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntLessThanInclusive("term", 1, 2, thrown());
        Verifier.requireIntLessThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireLessThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireIntLessThanExclusive("term", 1, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntLessThanExclusive("term", 1, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireMoreThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireLongMoreThanInclusive("term", 1, 0);
        Verifier.requireLongMoreThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongMoreThanInclusive("term", 1, 0, thrown());
        Verifier.requireLongMoreThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireMoreThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireLongMoreThanExclusive("term", 1, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongMoreThanExclusive("term", 1, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireLessThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireLongLessThanInclusive("term", 1, 2);
        Verifier.requireLongLessThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongLessThanInclusive("term", 1, 2, thrown());
        Verifier.requireLongLessThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireLessThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireLongLessThanExclusive("term", 1, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongLessThanExclusive("term", 1, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireMoreThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireFloatMoreThanInclusive("term", 1, 0);
        Verifier.requireFloatMoreThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatMoreThanInclusive("term", 1, 0, thrown());
        Verifier.requireFloatMoreThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireMoreThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireFloatMoreThanExclusive("term", 1, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatMoreThanExclusive("term", 1, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireLessThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireFloatLessThanInclusive("term", 1, 2);
        Verifier.requireFloatLessThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatLessThanInclusive("term", 1, 2, thrown());
        Verifier.requireFloatLessThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireLessThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireFloatLessThanExclusive("term", 1, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatLessThanExclusive("term", 1, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireMoreThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireDoubleMoreThanInclusive("term", 1, 0);
        Verifier.requireDoubleMoreThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleMoreThanInclusive("term", 1, 0, thrown());
        Verifier.requireDoubleMoreThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireMoreThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireDoubleMoreThanExclusive("term", 1, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive("term", 1, 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleMoreThanExclusive("term", 1, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive("term", 1, 2, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireLessThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireDoubleLessThanInclusive("term", 1, 2);
        Verifier.requireDoubleLessThanInclusive("term", 1, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleLessThanInclusive("term", 1, 2, thrown());
        Verifier.requireDoubleLessThanInclusive("term", 1, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireLessThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireDoubleLessThanExclusive("term", 1, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive("term", 1, 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive("term", 1, 0)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleLessThanExclusive("term", 1, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive("term", 1, 1, thrown())).isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive("term", 1, 0, thrown())).isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireMoreThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireBigDecimalMoreThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0));
        Verifier.requireBigDecimalMoreThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2)))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalMoreThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0), thrown());
        Verifier.requireBigDecimalMoreThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1), thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2), thrown()))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireMoreThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireBigDecimalMoreThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1)))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2)))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalMoreThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0), thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1), thrown()))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2), thrown()))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireLessThanInclusive() {
        // 測試檢查通過的情況
        Verifier.requireBigDecimalLessThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2));
        Verifier.requireBigDecimalLessThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0)))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalLessThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2), thrown());
        Verifier.requireBigDecimalLessThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1), thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0), thrown()))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireLessThanExclusive() {
        // 測試檢查通過的情況
        Verifier.requireBigDecimalLessThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1)))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0)))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalLessThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(2), thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(1), thrown()))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive("term", BigDecimal.valueOf(1), BigDecimal.valueOf(0), thrown()))
                .isInstanceOf(TestException.class);
    }

}
