package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.Test1RuntimeException;

import java.util.function.BiConsumer;

public class ShortRangeVerifierTest {

    @Test
    public void constructor() {
        String title = "kerker";

        ShortRangeVerifier rangeVerifier = Verifier.ofRange((short) 1, (short) 1);
        rangeVerifier = Verifier.ofRange((short) 1, (short) 2);
        rangeVerifier = Verifier.ofRange(title, (short) 1, (short) 1);
        rangeVerifier = Verifier.ofRange(title, (short) 1, (short) 2);

        AssertionsEx.awareThrown(() -> Verifier.ofRange((short) 1, (short) 0))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", (short) 1, "max", (short) 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Verifier.ofRange(title, (short) 1, (short) 0))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", (short) 1, "max", (short) 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3]
            // 測試檢查通過的情況
            rangeVerifier.requireInclusive(targetTerm, (short) 1);
            rangeVerifier.requireInclusive(targetTerm, (short) 2);
            rangeVerifier.requireInclusive(targetTerm, (short) 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, (short) 0))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, (short) 4))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireInclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireInclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireInclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, (short) 0, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, (short) 4, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void requireMinInclusiveMaxExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3)
            // 測試檢查通過的情況
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 1);
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 2);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 0))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 0, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void requireExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3)
            // 測試檢查通過的情況
            rangeVerifier.requireExclusive(targetTerm, (short) 2);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, (short) 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, (short) 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireExclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void requireMinExclusiveMaxInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3]
            // 測試檢查通過的情況
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 2);
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 4))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, (short) 4, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void refuseInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3]
            // 測試檢查通過的情況
            rangeVerifier.refuseInclusive(targetTerm, (short) 0);
            rangeVerifier.refuseInclusive(targetTerm, (short) 4);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, (short) 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, (short) 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, (short) 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseInclusive(targetTerm, (short) 0, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseInclusive(targetTerm, (short) 4, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void refuseMinInclusiveMaxExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3)
            // 測試檢查通過的情況
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 0);
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 0, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void refuseExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3)

            // 測試檢查通過的情況
            rangeVerifier.refuseExclusive(targetTerm, (short) 1);
            rangeVerifier.refuseExclusive(targetTerm, (short) 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseExclusive(targetTerm, (short) 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseExclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseExclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseExclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void refuseMinExclusiveMaxInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_EXCLUSIVE_INCLUSIVE;
        short min = 1;
        short max = 3;

        BiConsumer<String, ShortRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3]
            // 測試檢查通過的情況
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 1);
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 4);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 4, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, (short) 3, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

}
