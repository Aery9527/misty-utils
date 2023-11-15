package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class DoubleRangeVerifierTest {

    @Test
    public void constructor() {
        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(1d, 1d);
        rangeVerifier = Verifier.ofRange(1d, 2d);

        AssertionsEx.assertThrown(() -> Verifier.ofRange(1d, 0d)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(1d, 3d);

        // 測試檢查通過的情況
        rangeVerifier.requireInclusive("term", 1);
        rangeVerifier.requireInclusive("term", 2);
        rangeVerifier.requireInclusive("term", 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", 4)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireInclusive("term", 1, VerifierTest.thrown());
        rangeVerifier.requireInclusive("term", 2, VerifierTest.thrown());
        rangeVerifier.requireInclusive("term", 3, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", 0, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", 4, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void requireExclusive() {
        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(1d, 3d);

        // 測試檢查通過的情況
        rangeVerifier.requireExclusive("term", 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 3)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 4)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireExclusive("term", 2, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 0, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 1, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 3, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", 4, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseInclusive() {
        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(1d, 3d);

        // 測試檢查通過的情況
        rangeVerifier.refuseInclusive("term", 0);
        rangeVerifier.refuseInclusive("term", 4);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", 2)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", 3)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseInclusive("term", 0, VerifierTest.thrown());
        rangeVerifier.refuseInclusive("term", 4, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", 1, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", 2, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", 3, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseExclusive() {
        DoubleRangeVerifier rangeVerifier = Verifier.ofRange(1d, 3d);

        // 測試檢查通過的情況
        rangeVerifier.refuseExclusive("term", 1);
        rangeVerifier.refuseExclusive("term", 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive("term", 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseExclusive("term", 1, VerifierTest.thrown());
        rangeVerifier.refuseExclusive("term", 3, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive("term", 2, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

}
