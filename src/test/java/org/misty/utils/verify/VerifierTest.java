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
    public void refuseNull() {
        // 測試檢查通過的情況
        Verifier.refuseNull("term", "");
        Verifier.refuseNull("term", Collections.emptyList());
        Verifier.refuseNull("term", Collections.emptyMap());
        Verifier.refuseNull("term", new short[0]);
        Verifier.refuseNull("term", new int[0]);
        Verifier.refuseNull("term", new long[0]);
        Verifier.refuseNull("term", new float[0]);
        Verifier.refuseNull("term", new double[0]);
        Verifier.refuseNull("term", new boolean[0]);
        Verifier.refuseNull("term", new char[0]);
        Verifier.refuseNull("term", new byte[0]);
        Verifier.refuseNull("term", new Object[0]);
        Verifier.refuseNull("term", Optional.of(""));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.refuseNull("term", null)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.refuseNull("term", "", thrown());
        Verifier.refuseNull("term", Collections.emptyList(), thrown());
        Verifier.refuseNull("term", Collections.emptyMap(), thrown());
        Verifier.refuseNull("term", new short[0], thrown());
        Verifier.refuseNull("term", new int[0], thrown());
        Verifier.refuseNull("term", new long[0], thrown());
        Verifier.refuseNull("term", new float[0], thrown());
        Verifier.refuseNull("term", new double[0], thrown());
        Verifier.refuseNull("term", new boolean[0], thrown());
        Verifier.refuseNull("term", new char[0], thrown());
        Verifier.refuseNull("term", new byte[0], thrown());
        Verifier.refuseNull("term", new Object[0], thrown());
        Verifier.refuseNull("term", Optional.of(""), thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.refuseNull("term", null, thrown())).isInstanceOf(TestException.class);
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
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Optional.of(""))).isInstanceOf(IllegalArgumentException.class);

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
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 0);
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 1);
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0);
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 0, thrown());
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 1, thrown());
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0, thrown());
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 2, thrown()))
                .isInstanceOf(TestException.class)
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2));
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2, thrown()))
                .isInstanceOf(TestException.class)
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2));
    }

    @Test
    public void short_requireMoreThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 0);
        Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void short_requireLessThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 2);
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 1);
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2);
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 2, thrown());
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 1, thrown());
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2, thrown());
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void short_requireLessThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 2);
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 2, thrown());
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireMoreThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 0, thrown());
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 0, thrown());
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireMoreThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, 0, thrown());
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireLessThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 2, thrown());
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 2, thrown());
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void int_requireLessThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntLessThanExclusive(targetTerm, 1, 2, thrown());
        Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireMoreThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 0, thrown());
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 0, thrown());
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireMoreThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, 0, thrown());
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireLessThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 2, thrown());
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 2, thrown());
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void long_requireLessThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongLessThanExclusive(targetTerm, 1, 2, thrown());
        Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireMoreThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 0, thrown());
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 0, thrown());
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireMoreThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 0, thrown());
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireLessThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 2, thrown());
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 2, thrown());
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void float_requireLessThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, 2, thrown());
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireMoreThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 0, thrown());
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 0, thrown());
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireMoreThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 0, thrown());
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireLessThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 2, thrown());
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 1, thrown());
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 2, thrown());
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void double_requireLessThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 2, thrown());
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireMoreThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        BigDecimal $0 = BigDecimal.valueOf(0);
        BigDecimal $1 = BigDecimal.valueOf(1);
        BigDecimal $2 = BigDecimal.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $0);
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $1);
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $0);
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $0, thrown());
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $1, thrown());
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $0, thrown());
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $1, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireMoreThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        BigDecimal $0 = BigDecimal.valueOf(0);
        BigDecimal $1 = BigDecimal.valueOf(1);
        BigDecimal $2 = BigDecimal.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $0);
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $0, thrown());
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $0, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $2, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireLessThanInclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        BigDecimal $0 = BigDecimal.valueOf(0);
        BigDecimal $1 = BigDecimal.valueOf(1);
        BigDecimal $2 = BigDecimal.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $2);
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $1);
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $2);
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $2, thrown());
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $2, thrown());
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $2, thrown());
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(TestException.class);
    }

    @Test
    public void BigDecimal_requireLessThanExclusive() {
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        BigDecimal $0 = BigDecimal.valueOf(0);
        BigDecimal $1 = BigDecimal.valueOf(1);
        BigDecimal $2 = BigDecimal.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $2);
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $2, thrown());
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $2, thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $1, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(TestException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $0, thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(TestException.class);
    }

}
