package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.Test1RuntimeException;

import java.util.function.BiConsumer;

public class LongRangeVerifierTest {

    @Test
    public void constructor() {
        String title = "kerker";

        LongRangeVerifier rangeVerifier = Verifier.ofRange(1L, 1L);
        rangeVerifier = Verifier.ofRange(1L, 2L);
        rangeVerifier = Verifier.ofRange(title, 1L, 1L);
        rangeVerifier = Verifier.ofRange(title, 1L, 2L);

        AssertionsEx.awareThrown(() -> Verifier.ofRange(1, 0))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", 1, "max", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Verifier.ofRange(title, 1, 0))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", 1, "max", 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3]
            // 測試檢查通過的情況
            rangeVerifier.requireInclusive(targetTerm, 1);
            rangeVerifier.requireInclusive(targetTerm, 2);
            rangeVerifier.requireInclusive(targetTerm, 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, 0))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, 4))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException()))
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
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3)
            // 測試檢查通過的情況
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 1);
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 2);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 0))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
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
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3)
            // 測試檢查通過的情況
            rangeVerifier.requireExclusive(targetTerm, 2);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
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
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3]
            // 測試檢查通過的情況
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 2);
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 4))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException()))
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
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3]
            // 測試檢查通過的情況
            rangeVerifier.refuseInclusive(targetTerm, 0);
            rangeVerifier.refuseInclusive(targetTerm, 4);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseInclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
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
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3)
            // 測試檢查通過的情況
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 0);
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 0, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
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
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3)

            // 測試檢查通過的情況
            rangeVerifier.refuseExclusive(targetTerm, 1);
            rangeVerifier.refuseExclusive(targetTerm, 3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseExclusive(targetTerm, 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseExclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseExclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseExclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
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
        long min = 1;
        long max = 3;

        BiConsumer<String, LongRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3]
            // 測試檢查通過的情況
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 1);
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 4);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 4, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, 3, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

}
