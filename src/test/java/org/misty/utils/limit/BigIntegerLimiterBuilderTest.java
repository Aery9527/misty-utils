package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.BigIntegerRangeVerifier;
import org.misty.utils.verify.Verifier;

import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class BigIntegerLimiterBuilderTest {

    private final BigInteger $0 = BigInteger.ZERO;

    private final BigInteger $1 = BigInteger.valueOf(1);

    private final BigInteger $2 = BigInteger.valueOf(2);

    private final BigInteger $3 = BigInteger.valueOf(3);

    @Test
    public void min_max() {
        Consumer<UnaryOperator<BigIntegerLimiterBuilder>> test = setting -> {
            BigIntegerLimiterBuilder builder = Limiter.bigIntegerBuilder("AAA").giveMinLimit($1).giveMaxLimit($3);
            BigIntegerLimiter limiter = setting.apply(builder).build($2);
            AssertionsEx.assertThat(limiter.getMin()).isEqualTo($1);
            AssertionsEx.assertThat(limiter.getMax()).isEqualTo($3);
        };

        test.accept(BigIntegerLimiterBuilder::withBase);
        test.accept(BigIntegerLimiterBuilder::withVolatile);
        test.accept(BigIntegerLimiterBuilder::withAtomic);
    }

    @Test
    public void verifyMinLessThanMax() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        limiterBuilder.giveMinLimit($1, true).giveMaxLimit($1, true).build($1);
        limiterBuilder.giveMinLimit($1, true).giveMaxLimit($3, true).build($2);

        AssertionsEx.awareThrown(() -> limiterBuilder.giveMinLimit($1, true).giveMaxLimit($0, true).build($0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * 檢查限制上限, 且包含上限值, overflow檢查在{@link BigIntegerLimitVerifierHandlerTest}
     */
    @Test
    public void buildMaxLimitInclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        BigInteger max = $1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitInclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max.add($1))) // 超過上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max.add($1), limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(max.subtract($1), $1); // (max-1) + 1 = max, 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max, $1)) // max + 1 > max, 超過上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.add($1), limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, $1); // max - 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, $0.subtract($1))) // max - -1 > max, 超過上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.subtract($0.subtract($1)), limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制上限, 且不包含上限值, overflow檢查在{@link BigIntegerLimitVerifierHandlerTest}
     */
    @Test
    public void buildMaxLimitExclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.LESS_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MAX_LIMIT_TERM;
        String targetTerm = "kerker";
        BigInteger max = $1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildMaxLimitExclusiveVerifier(targetTerm, max, limiterThrown);

        // verifySet
        limitVerifier.verifySet(max.subtract($1)); // 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, limitTerm, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(max.subtract($2), $1); // (max-2) + 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max.subtract($1), $1)) // (max-1) + 1 = max, 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max.subtract($1), "+", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, $1); // max - 1 < max, 允許小於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max.subtract($1), $0.subtract($1))) // (max-1) - -1 = max, 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max.subtract($1), "-", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制下限, 且包含下限值, overflow檢查在{@link BigIntegerLimitVerifierHandlerTest}
     */
    @Test
    public void buildMinLimitInclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_INCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        BigInteger min = $1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitInclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min.subtract($1))) // 超過下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min.subtract($1), limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, $1); // min + 1 > min, 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, $0.subtract($1))) // min + -1 < min, 超過下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.add($0.subtract($1)), limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min.add($1), $1); // (min+1) - 1 = min, 允許等於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, $1)) // min - 1 < min, 超過下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.subtract($1), limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    /**
     * 檢查限制下限, 且不包含下限值, overflow檢查在{@link BigIntegerLimitVerifierHandlerTest}
     */
    @Test
    public void buildMinLimitExclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.MORE_THAN_EXCLUSIVE;
        String limitTerm = Limiter.ErrorMsgFormat.MIN_LIMIT_TERM;
        String targetTerm = "kerker";
        BigInteger min = $1;
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildMinLimitExclusiveVerifier(targetTerm, min, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min.add($1)); // 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, limitTerm, min))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, $1); // min + 1 > min, 允許大於下限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min.add($1), $0.subtract($1))) // (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min.add($1), "+", -1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min.add($2), $1); // (min+2) - 1 < min, 允許大於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min.add($1), $1))// (min+1) - 1 = min, 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min.add($1), "-", 1,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min, limitTerm, 1)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveInclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE;
        String targetTerm = "kerker";
        BigInteger min = $1;
        BigInteger max = $3;
        BigInteger avg = min.add(max).divide($2);
        BigInteger half = max.subtract(min).divide($2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min.subtract(half))) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min.subtract(half), min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max.add(half))) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max.add(half), min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min.add(half), $0.subtract(half)); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus(min.subtract(half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, $0.subtract(half))) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.add($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max, half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.add(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min.add(half), half); // 允許等於下限
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        limitVerifier.verifyMinus(max.add(half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.subtract(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, $0.subtract(half))) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.subtract($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeInclusiveExclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_INCLUSIVE_EXCLUSIVE;
        String targetTerm = "kerker";
        BigInteger min = $1;
        BigInteger max = $3;
        BigInteger avg = min.add(max).divide($2);
        BigInteger half = max.subtract(min).divide($2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeInclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(min); // 允許等於下限
        limitVerifier.verifySet(avg); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min.subtract(half))) // 不允許小於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min.subtract(half), min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max)) // 不允許等於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max, min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min.add(half), $0.subtract(half)); // 允許等於下限
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min, $0.subtract(half))) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "+", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.add($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max.subtract(half), half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max.subtract(half), "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.subtract(half).add(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(min.add(half), half); // 允許等於下限
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min, half)) // 不允許小於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min, "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.subtract(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max.subtract(half), $0.subtract(half))) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max.subtract(half), "-", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.subtract(half).subtract($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveExclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE;
        String targetTerm = "kerker";
        BigInteger min = $1;
        BigInteger max = $3;
        BigInteger avg = min.add(max).divide($2);
        BigInteger half = max.subtract(min).divide($2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveExclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

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
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min.add(half), $0.subtract(half))) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min.add(half), "+", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.add(half).add($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max.subtract(half), half)) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max.subtract(half), "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.subtract(half).add(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min.add(half), half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min.add(half), "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.add(half).subtract(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max.subtract(half), $0.subtract(half))) // 不允許等於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max.subtract(half), "-", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.subtract(half).subtract($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    @Test
    public void buildRangeExclusiveInclusiveVerifier() {
        BigIntegerLimiterBuilder limiterBuilder = genBuilder();

        String msgFormat = Verifier.ErrorMsgFormat.REQUIRE_RANGE_EXCLUSIVE_INCLUSIVE;
        String targetTerm = "kerker";
        BigInteger min = $1;
        BigInteger max = $3;
        BigInteger avg = min.add(max).divide($2);
        BigInteger half = max.subtract(min).divide($2);
        LimiterThrown limiterThrown = errorMsg -> {
            throw new TestRuntimeException(errorMsg);
        };

        BigIntegerRangeVerifier rangeVerifier = limiterBuilder.buildRangeVerifier(min, max);
        BigIntegerLimitVerifierHandler limitVerifier = limiterBuilder.buildRangeExclusiveInclusiveVerifier(targetTerm, rangeVerifier, limiterThrown);

        // verifySet
        limitVerifier.verifySet(avg); // 允許介於中間
        limitVerifier.verifySet(max); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(min)) // 不允許等於下限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, min, min, max))
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifySet(max.add(half))) // 不允許大於上限
                .hasMessage(targetTerm + " " + String.format(msgFormat, Limiter.ErrorMsgFormat.SET_TERM, max.add(half), min, max))
                .isInstanceOf(TestRuntimeException.class);

        // verifyPlus
        limitVerifier.verifyPlus(min, half); // 允許介於中間
        limitVerifier.verifyPlus(max.subtract(half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(min.add(half), $0.subtract(half))) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min.add(half), "+", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.add(half).add($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyPlus(max, half)) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "+", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.add(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);

        // verifyMinus
        limitVerifier.verifyMinus(max, half); // 允許介於中間
        limitVerifier.verifyMinus(max.add(half), half); // 允許等於上限
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(min.add(half), half)) // 不允許等於下限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, min.add(half), "-", half,
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, min.add(half).subtract(half), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
        AssertionsEx.awareThrown(() -> limitVerifier.verifyMinus(max, $0.subtract(half))) // 不允許大於上限
                .hasMessage(
                        String.format(Limiter.ErrorMsgFormat.OPERATION, targetTerm, max, "-", $0.subtract(half),
                                String.format(msgFormat, Limiter.ErrorMsgFormat.RESULT_TERM, max.subtract($0.subtract(half)), min, max)
                        )
                )
                .isInstanceOf(TestRuntimeException.class);
    }

    private BigIntegerLimiterBuilder genBuilder() {
        return Limiter.bigIntegerBuilder("AAA");
    }

}
