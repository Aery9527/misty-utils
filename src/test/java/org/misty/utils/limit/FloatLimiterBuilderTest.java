package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.FloatRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class FloatLimiterBuilderTest {

    @Test
    public void min_max() {
        float min = 1;
        float max = 3;

        Consumer<UnaryOperator<FloatLimiterBuilder>> test = setting -> {
            FloatLimiterBuilder builder = Limiter.floatLimiterBuilder("AAA").giveMinLimit(min).giveMaxLimit(max);
            FloatLimiter limiter = setting.apply(builder).build(2f);
            AssertionsEx.assertThat(limiter.getMin()).isEqualTo(min);
            AssertionsEx.assertThat(limiter.getMax()).isEqualTo(max);
        };

        test.accept(FloatLimiterBuilder::withBase);
        test.accept(FloatLimiterBuilder::withVolatile);
        test.accept(FloatLimiterBuilder::withAtomic);
    }

    @Test
    public void verifyMinLessThanMax() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        limiterBuilder.giveMinLimit(1f, true).giveMaxLimit(1f, true).build(1f);
        limiterBuilder.giveMinLimit(1f, true).giveMaxLimit(3f, true).build(2f);

        AssertionsEx.awareThrown(() -> limiterBuilder.giveMinLimit(1f, true).giveMaxLimit(0f, true).build(0f))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * 檢查限制上限, 且包含上限值, overflow檢查在{@link FloatLimitVerifierHandlerTest}
     */
    @Test
    public void buildMaxLimitInclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        float max = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitInclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max + 1)) // 超過上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + 1, limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(max - 1, 1); // (max-1) + 1 = max, 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max, 1)) // max + 1 > max, 超過上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", 1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max + 1, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, 1); // max - 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, -1)) // max - -1 > max, 超過上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -1, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制上限, 且不包含上限值, overflow檢查在{@link FloatLimitVerifierHandlerTest}
     */
    @Test
    public void buildMaxLimitExclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        float max = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitExclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet(max - 1); // 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(max - 2, 1); // (max-2) + 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max - 1, 1)) // (max-1) + 1 = max, 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - 1, "+", 1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, 1); // max - 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max - 1, -1)) // (max-1) - -1 = max, 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - 1, "-", -1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制下限, 且包含下限值, overflow檢查在{@link FloatLimitVerifierHandlerTest}
     */
    @Test
    public void buildMinLimitInclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        float min = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitInclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min - 1)) // 超過下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - 1, limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, 1); // min + 1 > min, 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, -1f)) // min + -1 < min, 超過下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -1, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min + 1, 1); // (min+1) - 1 = min, 允許等於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, 1)) // min - 1 < min, 超過下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", 1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - 1, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制下限, 且不包含下限值, overflow檢查在{@link FloatLimitVerifierHandlerTest}
     */
    @Test
    public void buildMinLimitExclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        float min = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitExclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min + 1); // 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, 1); // min + 1 > min, 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min + 1, -1f)) // (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + 1, "+", -1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min + 2, 1); // (min+2) - 1 < min, 允許大於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min + 1, 1))// (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + 1, "-", 1f,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1f)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveInclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        String targetTerm = "kerker";
        float min = 1;
        float max = 3;
        float avg = (min + max) / 2;
        float half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min - half)) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - half, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max + half)) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + half, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min + half, -half); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus(max - half, half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, -half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max, half)) // 不允許大於上限
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
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, -half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveExclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE;
        String targetTerm = "kerker";
        float min = 1;
        float max = 3;
        float avg = (min + max) / 2;
        float half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min - half)) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - half, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min + half, -half); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, -half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max - half, half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min + half, half); // 允許等於下限
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max - half, -half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveExclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE;
        String targetTerm = "kerker";
        float min = 1;
        float max = 3;
        float avg = (min + max) / 2;
        float half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(avg); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min + half, -half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max - half, half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min + half, half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max - half, -half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveInclusiveVerifier() {
        FloatLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE;
        String targetTerm = "kerker";
        float min = 1;
        float max = 3;
        float avg = (min + max) / 2;
        float half = (max - min) / 2;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        FloatRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        FloatLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max + half)) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + half, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus(max - half, half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min + half, -half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max, half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        limitVerifier.verifyMinus(max + half, half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min + half, half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, -half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    private FloatLimiterBuilder genBuilder() {
        return Limiter.floatLimiterBuilder("AAA");
    }

}
