package org.misty.utils.verify;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;

public class ShortRangeVerifierTest {

    @Test
    public void constructor() {
        ShortRangeVerifier rangeVerifier = Verifier.ofRange((short) 1, (short) 1);
        rangeVerifier = Verifier.ofRange((short) 1, (short) 2);

        AssertionsEx.assertThrown(() -> Verifier.ofRange((short) 1, (short) 0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void requireInclusive() {
        ShortRangeVerifier rangeVerifier = Verifier.ofRange((short) 1, (short) 3);

        // 測試檢查通過的情況
        rangeVerifier.requireInclusive("term", (short) 1);
        rangeVerifier.requireInclusive("term", (short) 2);
        rangeVerifier.requireInclusive("term", (short) 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", (short) 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", (short) 4)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireInclusive("term", (short) 1, VerifierTest.thrown());
        rangeVerifier.requireInclusive("term", (short) 2, VerifierTest.thrown());
        rangeVerifier.requireInclusive("term", (short) 3, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", (short) 0, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireInclusive("term", (short) 4, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void requireExclusive() {
        ShortRangeVerifier rangeVerifier = Verifier.ofRange((short) 1, (short) 3);

        // 測試檢查通過的情況
        rangeVerifier.requireExclusive("term", (short) 2);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 0)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 3)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 4)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.requireExclusive("term", (short) 2, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 0, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 1, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 3, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.requireExclusive("term", (short) 4, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseInclusive() {
        ShortRangeVerifier rangeVerifier = Verifier.ofRange((short) 1, (short) 3);

        // 測試檢查通過的情況
        rangeVerifier.refuseInclusive("term", (short) 0);
        rangeVerifier.refuseInclusive("term", (short) 4);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", (short) 1)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", (short) 2)).isInstanceOf(IllegalArgumentException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", (short) 3)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseInclusive("term", (short) 0, VerifierTest.thrown());
        rangeVerifier.refuseInclusive("term", (short) 4, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", (short) 1, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", (short) 2, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseInclusive("term", (short) 3, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

    @Test
    public void refuseExclusive() {
        ShortRangeVerifier rangeVerifier = Verifier.ofRange((short) 1, (short) 3);

        // 測試檢查通過的情況
        rangeVerifier.refuseExclusive("term", (short) 1);
        rangeVerifier.refuseExclusive("term", (short) 3);

        // 測試檢查不通過的情況
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive("term", (short) 2)).isInstanceOf(IllegalArgumentException.class);

        // 測試檢查通過的情況, 拋出非預設自定義錯誤
        rangeVerifier.refuseExclusive("term", (short) 1, VerifierTest.thrown());
        rangeVerifier.refuseExclusive("term", (short) 3, VerifierTest.thrown());

        // 測試檢查不通過的情況, 拋出非預設自定義錯誤
        AssertionsEx.assertThrown(() -> rangeVerifier.refuseExclusive("term", (short) 2, VerifierTest.thrown())).isInstanceOf(VerifierTest.TestException.class);
    }

}
