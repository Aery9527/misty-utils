package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class IntRangeVerifierTest {

    @Test
    public void constructor() {
        IntRangeVerifier rangeVerifier = Verifier.ofRange(1, 1);
        rangeVerifier = Verifier.ofRange(1, 2);

        AssertionsEx.assertThrown(() -> Verifier.ofRange(1, 0))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", 1, "max", 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        int min = 1;
        int max = 3;

        IntRangeVerifier rangeVerifier = Verifier.ofRange(min, max);

        // 測試檢查通過的情況
        rangeVerifier.requireInclusive(targetTerm, 1);
        rangeVerifier.requireInclusive(targetTerm, 2);
        rangeVerifier.requireInclusive(targetTerm, 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 4))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireInclusive(targetTerm, 1, VerifierTest.thrown());
        rangeVerifier.requireInclusive(targetTerm, 2, VerifierTest.thrown());
        rangeVerifier.requireInclusive(targetTerm, 3, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 0, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 4, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void requireExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE;
        int min = 1;
        int max = 3;

        IntRangeVerifier rangeVerifier = Verifier.ofRange(min, max);

        // 測試檢查通過的情況
        rangeVerifier.requireExclusive(targetTerm, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 3))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 4))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireExclusive(targetTerm, 2, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 0, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 1, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 3, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 4, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE;
        int min = 1;
        int max = 3;

        IntRangeVerifier rangeVerifier = Verifier.ofRange(min, max);

        // 測試檢查通過的情況
        rangeVerifier.refuseInclusive(targetTerm, 0);
        rangeVerifier.refuseInclusive(targetTerm, 4);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 3))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseInclusive(targetTerm, 0, VerifierTest.thrown());
        rangeVerifier.refuseInclusive(targetTerm, 4, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 1, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 2, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 3, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE;
        int min = 1;
        int max = 3;

        IntRangeVerifier rangeVerifier = Verifier.ofRange(min,max);

        // 測試檢查通過的情況
        rangeVerifier.refuseExclusive(targetTerm, 1);
        rangeVerifier.refuseExclusive(targetTerm, 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive(targetTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseExclusive(targetTerm, 1, VerifierTest.thrown());
        rangeVerifier.refuseExclusive(targetTerm, 3, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive(targetTerm, 2, VerifierTest.thrown()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                .isInstanceOf(VerifierTest.TestException.class);
    }

}
