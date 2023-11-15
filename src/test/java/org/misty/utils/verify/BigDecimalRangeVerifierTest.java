package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

import java.math.BigDecimal;

public class BigDecimalRangeVerifierTest {

    @Test
    public void constructor() {
        BigDecimalRangeVerifier rangeVerifier = Verifier.ofRange(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        rangeVerifier = Verifier.ofRange(BigDecimal.valueOf(1), BigDecimal.valueOf(2));

        AssertionsEx.assertThrown(() -> Verifier.ofRange(BigDecimal.valueOf(1), BigDecimal.valueOf(0))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        BigDecimalRangeVerifier rangeVerifier = Verifier.ofRange(BigDecimal.valueOf(1), BigDecimal.valueOf(3));

        // 測試檢查通過的情況
        rangeVerifier.requireInclusive("term", BigDecimal.valueOf(1));
        rangeVerifier.requireInclusive("term", BigDecimal.valueOf(2));
        rangeVerifier.requireInclusive("term", BigDecimal.valueOf(3));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", BigDecimal.valueOf(0))).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", BigDecimal.valueOf(4))).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireInclusive("term", BigDecimal.valueOf(1), VerifierTest.thrown());
        rangeVerifier.requireInclusive("term", BigDecimal.valueOf(2), VerifierTest.thrown());
        rangeVerifier.requireInclusive("term", BigDecimal.valueOf(3), VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", BigDecimal.valueOf(0), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", BigDecimal.valueOf(4), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void requireExclusive() {
        BigDecimalRangeVerifier rangeVerifier = Verifier.ofRange(BigDecimal.valueOf(1), BigDecimal.valueOf(3));

        // 測試檢查通過的情況
        rangeVerifier.requireExclusive("term", BigDecimal.valueOf(2));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(0))).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(1))).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(3))).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(4))).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireExclusive("term", BigDecimal.valueOf(2), VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(0), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(1), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(3), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", BigDecimal.valueOf(4), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseInclusive() {
        BigDecimalRangeVerifier rangeVerifier = Verifier.ofRange(BigDecimal.valueOf(1), BigDecimal.valueOf(3));

        // 測試檢查通過的情況
        rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(0));
        rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(4));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(1))).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(2))).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(3))).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(0), VerifierTest.thrown());
        rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(4), VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(1), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(2), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", BigDecimal.valueOf(3), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseExclusive() {
        BigDecimalRangeVerifier rangeVerifier = Verifier.ofRange(BigDecimal.valueOf(1), BigDecimal.valueOf(3));

        // 測試檢查通過的情況
        rangeVerifier.refuseExclusive("term", BigDecimal.valueOf(1));
        rangeVerifier.refuseExclusive("term", BigDecimal.valueOf(3));

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive("term", BigDecimal.valueOf(2))).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseExclusive("term", BigDecimal.valueOf(1), VerifierTest.thrown());
        rangeVerifier.refuseExclusive("term", BigDecimal.valueOf(3), VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive("term", BigDecimal.valueOf(2), VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

}
