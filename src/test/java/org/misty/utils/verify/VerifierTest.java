package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.Test1RuntimeException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;

public class VerifierTest {

    public static <TargetType, MagType extends VerifierErrorMsg<TargetType>> VerifierThrown<TargetType, MagType, Test1RuntimeException> throwTest1RuntimeException() {
        return error -> {
            throw new Test1RuntimeException(error.getErrorMsg());
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
        Verifier.refuseNull("term", "", throwTest1RuntimeException());
        Verifier.refuseNull("term", Collections.emptyList(), throwTest1RuntimeException());
        Verifier.refuseNull("term", Collections.emptyMap(), throwTest1RuntimeException());
        Verifier.refuseNull("term", new short[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new int[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new long[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new float[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new double[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new boolean[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new char[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new byte[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", new Object[0], throwTest1RuntimeException());
        Verifier.refuseNull("term", Optional.of(""), throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.refuseNull("term", null, throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
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
        Verifier.refuseNullOrEmpty("term", new Object(), throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", "123", throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", Collections.singletonList("A"), throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", Collections.singletonMap("A", "B"), throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new short[]{1}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new int[]{1}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new long[]{1}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new float[]{1}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new double[]{1}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new boolean[]{true}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new char[]{' '}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new byte[]{1}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", new Object[]{new Object()}, throwTest1RuntimeException());
        Verifier.refuseNullOrEmpty("term", Optional.of("A"), throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", null, throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", "", throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Collections.emptyList(), throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Collections.emptyMap(), throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new short[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new int[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new long[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new float[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new double[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new boolean[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new char[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new byte[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", new Object[0], throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.refuseNullOrEmpty("term", Optional.ofNullable(null), throwTest1RuntimeException())).isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void short_requireMoreThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 0);
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 1);
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0);
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1);
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, (short) 0);
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, (short) 1);
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 0);
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 0, throwTest1RuntimeException());
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 1, throwTest1RuntimeException());
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0, throwTest1RuntimeException());
        Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException());
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, (short) 0, throwTest1RuntimeException());
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, (short) 1, throwTest1RuntimeException());
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 0, throwTest1RuntimeException());
        Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, (short) 2, throwTest1RuntimeException()))
                .isInstanceOf(Test1RuntimeException.class)
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2));
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException()))
                .isInstanceOf(Test1RuntimeException.class)
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2));
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, (short) 2, throwTest1RuntimeException()))
                .isInstanceOf(Test1RuntimeException.class)
                .hasMessageContaining(title)
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2));
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException()))
                .isInstanceOf(Test1RuntimeException.class)
                .hasMessageContaining(title)
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2));
    }

    @Test
    public void short_requireMoreThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 0);
        Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 0);
        Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, (short) 0);
        Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 0);

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
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 0, throwTest1RuntimeException());
        Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, (short) 0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, (short) 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, (short) 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortMoreThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void short_requireLessThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 2);
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 1);
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2);
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1);
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, (short) 2);
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, (short) 1);
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 2);
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 2, throwTest1RuntimeException());
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 1, throwTest1RuntimeException());
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException());
        Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException());
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, (short) 2, throwTest1RuntimeException());
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, (short) 1, throwTest1RuntimeException());
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException());
        Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(targetTerm, (short) 1, limitTerm, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanInclusive(title, targetTerm, (short) 1, limitTerm, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void short_requireLessThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 2);
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 2);
        Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, (short) 2);
        Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 2);

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
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 2, throwTest1RuntimeException());
        Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException());
        Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, (short) 2, throwTest1RuntimeException());
        Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(targetTerm, (short) 1, limitTerm, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, "", (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireShortLessThanExclusive(title, targetTerm, (short) 1, limitTerm, (short) 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, (short) 1, limitTerm, (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void int_requireMoreThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, 0);
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, limitTerm, 0);
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void int_requireMoreThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, 0);
        Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, limitTerm, 0);

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
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntMoreThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void int_requireLessThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, 2);
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, limitTerm, 2);
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireIntLessThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireIntLessThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void int_requireLessThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireIntLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireIntLessThanExclusive(title, targetTerm, 1, 2);
        Verifier.requireIntLessThanExclusive(title, targetTerm, 1, limitTerm, 2);

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
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireIntLessThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireIntLessThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireIntLessThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireIntLessThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void long_requireMoreThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, 0);
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, limitTerm, 0);
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void long_requireMoreThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, 0);
        Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, limitTerm, 0);

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
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongMoreThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void long_requireLessThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, 2);
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, limitTerm, 2);
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireLongLessThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireLongLessThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void long_requireLessThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireLongLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireLongLessThanExclusive(title, targetTerm, 1, 2);
        Verifier.requireLongLessThanExclusive(title, targetTerm, 1, limitTerm, 2);

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
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireLongLessThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireLongLessThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireLongLessThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, "", 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireLongLessThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void float_requireMoreThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, 0);
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, limitTerm, 0);
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void float_requireMoreThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, 0);
        Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, limitTerm, 0);

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
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 2f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatMoreThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 2f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void float_requireLessThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, 2);
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, limitTerm, 2);
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void float_requireLessThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, 2);
        Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, limitTerm, 2);

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
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 1f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, "", 0f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 1f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireFloatLessThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1f, limitTerm, 0f))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void double_requireMoreThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 0);
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 1);
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, 0);
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, limitTerm, 0);
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void double_requireMoreThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 0);
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 0);
        Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, 0);
        Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, limitTerm, 0);

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
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, limitTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException());
        Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 2d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleMoreThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 2d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void double_requireLessThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 2);
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 1);
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 1);
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, 2);
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, 1);
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, limitTerm, 2);
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, limitTerm, 1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, 1, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanInclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void double_requireLessThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        // 測試檢查通過的情況
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 2);
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 2);
        Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, 2);
        Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, limitTerm, 2);

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
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, limitTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, limitTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, 2, throwTest1RuntimeException());
        Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, limitTerm, 2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 1d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, "", 0d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, limitTerm, 1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 1d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireDoubleLessThanExclusive(title, targetTerm, 1, limitTerm, 0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, limitTerm, 0d))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigDecimal_requireMoreThanInclusive() {
        String title = "kerker";
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
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, $0);
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, $1);
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, limitTerm, $0);
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, limitTerm, $1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $1, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $1, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, $1, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, limitTerm, $1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanInclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigDecimal_requireMoreThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        BigDecimal $0 = BigDecimal.valueOf(0);
        BigDecimal $1 = BigDecimal.valueOf(1);
        BigDecimal $2 = BigDecimal.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $0);
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $0);
        Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, $0);
        Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, limitTerm, $0);

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
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalMoreThanExclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigDecimal_requireLessThanInclusive() {
        String title = "kerker";
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
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, $2);
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, $1);
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, limitTerm, $2);
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, limitTerm, $1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanInclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigDecimal_requireLessThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        BigDecimal $0 = BigDecimal.valueOf(0);
        BigDecimal $1 = BigDecimal.valueOf(1);
        BigDecimal $2 = BigDecimal.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $2);
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $2);
        Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, $2);
        Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, limitTerm, $2);

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
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigDecimalLessThanExclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigInteger_requireMoreThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;

        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, $0);
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, $1);
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, limitTerm, $0);
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, limitTerm, $1);
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, $0);
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, $1);
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, limitTerm, $0);
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, limitTerm, $1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, $1, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, limitTerm, $1, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, $1, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, limitTerm, $1, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanInclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigInteger_requireMoreThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;

        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, $0);
        Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, limitTerm, $0);
        Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, $0);
        Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, limitTerm, $0);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, $0, throwTest1RuntimeException());
        Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerMoreThanExclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $2))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigInteger_requireLessThanInclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;

        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, $2);
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, $1);
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, limitTerm, $2);
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, limitTerm, $1);
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, $2);
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, $1);
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, limitTerm, $2);
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, limitTerm, $1);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanInclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void BigInteger_requireLessThanExclusive() {
        String title = "kerker";
        String targetTerm = "targetTerm";
        String limitTerm = "limitTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;

        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);

        // 測試檢查通過的情況
        Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, $2);
        Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, limitTerm, $2);
        Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, $2);
        Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, limitTerm, $2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, $2, throwTest1RuntimeException());
        Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, limitTerm, $2, throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, "", $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, limitTerm, $1, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $1))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> Verifier.requireBigIntegerLessThanExclusive(title, targetTerm, $1, limitTerm, $0, throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, $1, limitTerm, $0))
                .hasMessageContaining(title)
                .isInstanceOf(Test1RuntimeException.class);
    }

}
