package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.ShortRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class ShortLimiterBuilderTest {

    @Test
    public void min_max() {
        short min = 1;
        short max = 3;

        Consumer<UnaryOperator<ShortLimiterBuilder>> test = setting -> {
            ShortLimiterBuilder builder = Limiter.shortLimiterBuilder("AAA").giveMinLimit(min).giveMaxLimit(max);
            ShortLimiter limiter = setting.apply(builder).build((short) 2);
            AssertionsEx.assertThat(limiter.getMin()).isEqualTo(min);
            AssertionsEx.assertThat(limiter.getMax()).isEqualTo(max);
        };

        test.accept(ShortLimiterBuilder::withBase);
        test.accept(ShortLimiterBuilder::withVolatile);
        test.accept(ShortLimiterBuilder::withAtomic);
    }

    @Test
    public void verifyMinLessThanMax() {
        ShortLimiterBuilder limiterBuilder = genBuilder();

        limiterBuilder.giveMinLimit((short) 1, true).giveMaxLimit((short) 1, true).build((short) 1);
        limiterBuilder.giveMinLimit((short) 1, true).giveMaxLimit((short) 3, true).build((short) 2);

        AssertionsEx.awareThrown(() -> limiterBuilder.giveMinLimit((short) 1, true).giveMaxLimit((short) 0, true).build((short) 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * 檢查限制上限, 且包含上限值, overflow檢查在{@link ShortLimitVerifierHandlerTest}
     */
    @Test
    public void buildMaxLimitInclusiveVerifier() {
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        short max = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitInclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet((short) (max + 1))) // 超過上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + 1, limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus((short) (max - 1), (short) 1); // (max-1) + 1 = max, 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max, (short) 1)) // max + 1 > max, 超過上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max + 1, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, (short) 1); // max - 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, (short) -1)) // max - -1 > max, 超過上限
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
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        short max = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitExclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet((short) (max - 1)); // 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus((short) (max - 2), (short) 1); // (max-2) + 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus((short) (max - 1), (short) 1)) // (max-1) + 1 = max, 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - 1, "+", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, (short) 1); // max - 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus((short) (max - 1), (short) -1)) // (max-1) - -1 = max, 不允許等於上限
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
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        short min = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitInclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet((short) (min - 1))) // 超過下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - 1, limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, (short) 1); // min + 1 > min, 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, (short) -1)) // min + -1 < min, 超過下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -1, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus((short) (min + 1), (short) 1); // (min+1) - 1 = min, 允許等於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, (short) 1)) // min - 1 < min, 超過下限
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
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        short min = 1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitExclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet((short) (min + 1)); // 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, (short) 1); // min + 1 > min, 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus((short) (min + 1), (short) -1)) // (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + 1, "+", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus((short) (min + 2), (short) 1); // (min+2) - 1 < min, 允許大於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus((short) (min + 1), (short) 1))// (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + 1, "-", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveInclusiveVerifier() {
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        String targetTerm = "kerker";
        short min = 1;
        short max = 3;
        short avg = (short) ((min + max) / 2);
        short half = (short) ((max - min) / 2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet((short) (min - half))) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - half, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet((short) (max + half))) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + half, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus((short) (min + half), (short) -half); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus((short) (max - half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, (short) (-half))) // 不允許小於下限
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
        limitVerifier.verifyMinus((short) (min + half), half); // 允許等於下限
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        limitVerifier.verifyMinus((short) (max + half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, (short) (-half))) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveExclusiveVerifier() {
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE;
        String targetTerm = "kerker";
        short min = 1;
        short max = 3;
        short avg = (short) ((min + max) / 2);
        short half = (short) ((max - min) / 2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet((short) (min - half))) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min - half, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus((short) (min + half), (short) -half); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, (short) -half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus((short) (max - half), half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus((short) (min + half), half); // 允許等於下限
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus((short) (max - half), (short) -half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveExclusiveVerifier() {
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE;
        String targetTerm = "kerker";
        short min = 1;
        short max = 3;
        short avg = (short) ((min + max) / 2);
        short half = (short) ((max - min) / 2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

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
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus((short) (min + half), (short) -half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "+", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half + -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus((short) (max - half), half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half + half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus((short) (min + half), half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus((short) (max - half), (short) -half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max - half, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - half - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveInclusiveVerifier() {
        ShortLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE;
        String targetTerm = "kerker";
        short min = 1;
        short max = 3;
        short avg = (short) ((min + max) / 2);
        short half = (short) ((max - min) / 2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        ShortRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        ShortLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet((short) (max + half))) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max + half, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus((short) (max - half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus((short) (min + half), (short) -half)) // 不允許等於下限
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
        limitVerifier.verifyMinus((short) (max + half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus((short) (min + half), half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min + half, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min + half - half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, (short) -half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max - -half, min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    private ShortLimiterBuilder genBuilder() {
        return Limiter.shortLimiterBuilder("AAA");
    }

}
