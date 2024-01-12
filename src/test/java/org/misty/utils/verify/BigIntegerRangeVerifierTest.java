package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.Test1RuntimeException;

import java.math.BigInteger;
import java.util.function.BiConsumer;

public class BigIntegerRangeVerifierTest {

    @Test
    public void constructor() {
        String title = "kerker";
        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);

        BigIntegerRangeVerifier rangeVerifier = Verifier.ofRange($1, $1);
        rangeVerifier = Verifier.ofRange($1, $2);
        rangeVerifier = Verifier.ofRange(title, $1, $1);
        rangeVerifier = Verifier.ofRange(title, $1, $2);

        AssertionsEx.awareThrown(() -> Verifier.ofRange($1, $0))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", 1, "max", 0))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.awareThrown(() -> Verifier.ofRange(title, $1, $0))
                .hasMessageContaining(String.format(Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE, "min", 1, "max", 0))
                .hasMessageContaining(title)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger $4 = BigInteger.valueOf(4);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3]
            // 測試檢查通過的情況
            rangeVerifier.requireInclusive(targetTerm, $1);
            rangeVerifier.requireInclusive(targetTerm, $2);
            rangeVerifier.requireInclusive(targetTerm, $3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, $0))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, $4))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireInclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireInclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireInclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, $0, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireInclusive(targetTerm, $4, VerifierTest.throwTest1RuntimeException()))
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
        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3)
            // 測試檢查通過的情況
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $1);
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $2);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $0))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $0, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 0, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinInclusiveMaxExclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException()))
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
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3)
            // 測試檢查通過的情況
            rangeVerifier.requireExclusive(targetTerm, $2);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, $1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, $3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireExclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireExclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException()))
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
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger $4 = BigInteger.valueOf(4);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3]
            // 測試檢查通過的情況
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $2);
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $4))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 4, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.requireMinExclusiveMaxInclusive(targetTerm, $4, VerifierTest.throwTest1RuntimeException()))
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
        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger $4 = BigInteger.valueOf(4);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3]
            // 測試檢查通過的情況
            rangeVerifier.refuseInclusive(targetTerm, $0);
            rangeVerifier.refuseInclusive(targetTerm, $4);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, $1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, $2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, $3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseInclusive(targetTerm, $0, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseInclusive(targetTerm, $4, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseInclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

    @Test
    public void refuseMinInclusiveMaxExclusive() {
        String targetTerm = "targetTerm";
        String errorMsgFormat = Verifier.ErrorMsgFormat.REFUSE_RANGE_INCLUSIVE_EXCLUSIVE;
        BigInteger $0 = BigInteger.valueOf(0);
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 [1, 3)
            // 測試檢查通過的情況
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $0);
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $1))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $0, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 1, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinInclusiveMaxExclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException()))
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
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3)
            // 測試檢查通過的情況
            rangeVerifier.refuseExclusive(targetTerm, $1);
            rangeVerifier.refuseExclusive(targetTerm, $3);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseExclusive(targetTerm, $2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseExclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseExclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseExclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException()))
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
        BigInteger $1 = BigInteger.valueOf(1);
        BigInteger $2 = BigInteger.valueOf(2);
        BigInteger $3 = BigInteger.valueOf(3);
        BigInteger $4 = BigInteger.valueOf(4);
        BigInteger min = BigInteger.valueOf(1);
        BigInteger max = BigInteger.valueOf(3);

        BiConsumer<String, BigIntegerRangeVerifier> test = (title, rangeVerifier) -> { // 測試 (1, 3]
            // 測試檢查通過的情況
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $1);
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $4);

            // 測試檢查不通過的情況
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $2))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $3))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(IllegalArgumentException.class);

            // 測試檢查通過的情況, 拋出非預設自定義錯誤
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $1, VerifierTest.throwTest1RuntimeException());
            rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $4, VerifierTest.throwTest1RuntimeException());

            // 測試檢查不通過的情況, 拋出非預設自定義錯誤
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $2, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 2, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
            AssertionsEx.awareThrown(() -> rangeVerifier.refuseMinExclusiveMaxInclusive(targetTerm, $3, VerifierTest.throwTest1RuntimeException()))
                    .hasMessageContaining(String.format(errorMsgFormat, targetTerm, 3, min, max))
                    .hasMessageContaining(title)
                    .isInstanceOf(Test1RuntimeException.class);
        };

        test.accept("", Verifier.ofRange(min, max));
        test.accept("kerker", Verifier.ofRange("kerker", min, max));
    }

}
