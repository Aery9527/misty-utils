package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.Test1RuntimeException;

public class DoubleRangeVerifierTest {

    @Test
    public void constructor() {
        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(1d, 1d);
        rangeVerifier = Verifier.ofRange(1d, 2d);

        AssertionsEx.assertThrown(() -> Verifier.ofRange(1d, 0d))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", 1d, "max", 0d))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // [1, 3]

        // 測試檢查通過的情況
        rangeVerifier.requireInclusive(targetTerm, 1);
        rangeVerifier.requireInclusive(targetTerm, 2);
        rangeVerifier.requireInclusive(targetTerm, 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 4))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.requireInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.requireInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void requireMinInclusiveMaxExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // [1, 3)

        // 測試檢查通過的情況
        rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 1);
        rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 0))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 3))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void requireExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // (1, 3)

        // 測試檢查通過的情況
        rangeVerifier.requireExclusive(targetTerm, 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 3))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void requireMinExclusiveMaxInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // (1, 3]

        // 測試檢查通過的情況
        rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 2);
        rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 4))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void refuseInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // [1, 3]

        // 測試檢查通過的情況
        rangeVerifier.refuseInclusive(targetTerm, 0);
        rangeVerifier.refuseInclusive(targetTerm, 4);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 3))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseInclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.refuseInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void refuseMinInclusiveMaxExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // [1, 3)

        // 測試檢查通過的情況
        rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 0);
        rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 1))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void refuseExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // (1, 3)

        // 測試檢查通過的情況
        rangeVerifier.refuseExclusive(targetTerm, 1);
        rangeVerifier.refuseExclusive(targetTerm, 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive(targetTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.refuseExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

    @Test
    public void refuseMinExclusiveMaxInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE;
        double min = 1;
        double max = 3;

        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(min, max); // (1, 3]

        // 測試檢查通過的情況
        rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 1);
        rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 4);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 2))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 3))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
        rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
                .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3d, min, max))
                .isInstanceOf(Test1RuntimeException.class);
    }

}
