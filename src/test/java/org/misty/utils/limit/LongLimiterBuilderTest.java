package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.LongRangeVerifier;
import org.misty.utils.verify.Verifier;

public class LongLimiterBuilderTest {

    @Test
    public void verifyMinLessThanMax() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        limiterBuilder.giveMinLimit(1l, true).giveMaxLimit(1l, true).build(1l);
        limiterBuilder.giveMinLimit(1l, true).giveMaxLimit(3l, true).build(2l);

        AssertionsEx.assertThrown(() -> limiterBuilder.giveMinLimit(1l, true).giveMaxLimit(0l, true).build(0l))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * 檢查限制上限, 且包含上限值, overflow檢查在{@link ShortLimitVerifierHandlerTest}
     */
    @Test
    public void buildMaxLimitInclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        long max = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitInclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(max + 1)) // 超過上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + 1, limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(max - 1, 1); // (max-1) + 1 = max, 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(max, 1)) // max + 1 > max, 超過上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max + 1, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, 1); // max - 1 < max, 允許小於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(max, -1)) // max - -1 > max, 超過上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -1, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制上限, 且不包含上限值, overflow檢查在{@link ShortLimitVerifierHandlerTest}
     */
    @Test
    public void buildMaxLimitExclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        long max = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitExclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet(max - 1); // 允許小於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(max - 2, 1); // (max-2) + 1 < max, 允許小於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(max - 1, 1)) // (max-1) + 1 = max, 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - 1, "+", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, 1); // max - 1 < max, 允許小於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(max - 1, -1)) // (max-1) - -1 = max, 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - 1, "-", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制下限, 且包含下限值, overflow檢查在{@link ShortLimitVerifierHandlerTest}
     */
    @Test
    public void buildMinLimitInclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        long min = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitInclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(min - 1)) // 超過下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - 1, limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, 1); // min + 1 > min, 允許大於下限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(min, -1)) // min + -1 < min, 超過下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -1, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min + 1, 1); // (min+1) - 1 = min, 允許等於下限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(min, 1)) // min - 1 < min, 超過下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - 1, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制下限, 且不包含下限值, overflow檢查在{@link ShortLimitVerifierHandlerTest}
     */
    @Test
    public void buildMinLimitExclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        long min = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitExclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min + 1); // 允許大於下限
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, 1); // min + 1 > min, 允許大於下限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(min + 1, -1)) // (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + 1, "+", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min + 2, 1); // (min+2) - 1 < min, 允許大於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(min + 1, 1))// (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + 1, "-", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveInclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        String targetTerm = "kerker";
        long min = 1;
        long max = 3;
        long avg = (min + max) / 2;
        long half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(min - half)) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - half, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(max + half)) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + half, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min + half, -half); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus(max - half, half); // 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(min, -half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(max, half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min + half, half); // 允許等於下限
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        limitVerifier.verifyMinus(max + half, half); // 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(max, -half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveExclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE;
        String targetTerm = "kerker";
        long min = 1;
        long max = 3;
        long avg = (min + max) / 2;
        long half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(min - half)) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - half, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min + half, -half); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(min, -half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(max - half, half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min + half, half); // 允許等於下限
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(max - half, -half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveExclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE;
        String targetTerm = "kerker";
        long min = 1;
        long max = 3;
        long avg = (min + max) / 2;
        long half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(avg); // 允許介於中間
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(min + half, -half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(max - half, half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(min + half, half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(max - half, -half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveInclusiveVerifier() {
        LongLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE;
        String targetTerm = "kerker";
        long min = 1;
        long max = 3;
        long avg = (min + max) / 2;
        long half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        LongRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        LongLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifySet(max + half)) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + half, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus(max - half, half); // 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(min + half, -half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyPlus(max, half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        limitVerifier.verifyMinus(max + half, half); // 允許等於上限
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(min + half, half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.assertThrown(() -> limitVerifier.verifyMinus(max, -half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    private LongLimiterBuilder genBuilder() {
        return Limiter.longLimiterBuilder("AAA");
    }

}
