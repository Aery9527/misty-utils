package org.misty.utils.limit;

import org.junit.jupiter.api.Test;
import org.misty._utils.AssertionsEx;
import org.misty._utils.TestRuntimeException;
import org.misty.utils.verify.IntRangeVerifier;
import org.misty.utils.verify.Verifier;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class AbstractLimiterBuilderTest {

    public static class AbstractLimiterTestBuilder extends
            AbstractLimiterBuilder<
                    Integer,
                    IntLimitVerifierHandler,
                    IntRangeVerifier,
                    IntLimiter,
                    IntMildLimiter,
                    AbstractLimiterTestBuilder
                    > {

        @Override
        protected void verifyMinLessThanMax(Integer min, Integer max) throws IllegalArgumentException {

        }

        @Override
        protected IntRangeVerifier buildRangeVerifier(Integer min, Integer max) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildMaxLimitInclusiveVerifier(String targetTerm, Integer max, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildMaxLimitExclusiveVerifier(String targetTerm, Integer max, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildMinLimitInclusiveVerifier(String targetTerm, Integer min, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildMinLimitExclusiveVerifier(String targetTerm, Integer min, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildRangeInclusiveInclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildRangeInclusiveExclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildRangeExclusiveExclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimitVerifierHandler buildRangeExclusiveInclusiveVerifier(String targetTerm, IntRangeVerifier rangeVerifier, LimiterThrown limiterThrown) {
            return null;
        }

        @Override
        protected IntLimiter buildBaseLimiter(IntLimitVerifierHandler verifier, Integer initValue) {
            return null;
        }

        @Override
        protected IntLimiter buildVolatileLimiter(IntLimitVerifierHandler verifier, Integer initValue) {
            return null;
        }

        @Override
        protected IntLimiter buildAtomicLimiter(IntLimitVerifierHandler verifier, Integer initValue) {
            return null;
        }

        @Override
        protected IntMildLimiter wrapMildLimiter(IntLimiter limiter) {
            return null;
        }
    }

    @Test
    public void givenTargetTerm() {
        AbstractLimiterTestBuilder testBuilder = new AbstractLimiterTestBuilder();

        AssertionsEx.assertThat(testBuilder.getTargetTerm()).isEqualTo(null);

        String targetTerm = "kerker";
        testBuilder.giveTargetTerm(targetTerm);

        AssertionsEx.assertThat(testBuilder.getTargetTerm()).isEqualTo(targetTerm);
    }

    @Test
    public void givenMinLimit() {
        AbstractLimiterTestBuilder testBuilder = new AbstractLimiterTestBuilder();

        AssertionsEx.assertThat(testBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(testBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMaxInclusive()).isTrue();

        testBuilder.giveMinLimit(1, false);
        AssertionsEx.assertThat(testBuilder.getMin()).isEqualTo(1);
        AssertionsEx.assertThat(testBuilder.isMinInclusive()).isFalse();
        AssertionsEx.assertThat(testBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMaxInclusive()).isTrue();

        testBuilder.giveMinLimit(2, true);
        AssertionsEx.assertThat(testBuilder.getMin()).isEqualTo(2);
        AssertionsEx.assertThat(testBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(testBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMaxInclusive()).isTrue();
    }

    @Test
    public void givenMaxLimit() {
        AbstractLimiterTestBuilder testBuilder = new AbstractLimiterTestBuilder();

        AssertionsEx.assertThat(testBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(testBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMaxInclusive()).isTrue();

        testBuilder.giveMaxLimit(1, false);
        AssertionsEx.assertThat(testBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(testBuilder.getMax()).isEqualTo(1);
        AssertionsEx.assertThat(testBuilder.isMaxInclusive()).isFalse();

        testBuilder.giveMaxLimit(2, true);
        AssertionsEx.assertThat(testBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(testBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(testBuilder.getMax()).isEqualTo(2);
        AssertionsEx.assertThat(testBuilder.isMaxInclusive()).isTrue();
    }

    @Test
    public void givenThrown() {
        AbstractLimiterTestBuilder testBuilder = new AbstractLimiterTestBuilder();

        AssertionsEx.assertThat(testBuilder.getLimiterThrown()).isEqualTo(null);

        LimiterThrown limiterThrown = error -> {
        };
        testBuilder.giveThrown(limiterThrown);

        AssertionsEx.assertThat(testBuilder.getLimiterThrown() == limiterThrown).isTrue();
    }

    @Test
    public void declareType() {
        AbstractLimiterTestBuilder testBuilder = new AbstractLimiterTestBuilder();

        AssertionsEx.assertThat(testBuilder.getDeclareType()).isEqualTo(AbstractLimiterBuilder.DeclareType.BASE);

        testBuilder.withVolatile();
        AssertionsEx.assertThat(testBuilder.getDeclareType()).isEqualTo(AbstractLimiterBuilder.DeclareType.VOLATILE);

        testBuilder.withAtomic();
        AssertionsEx.assertThat(testBuilder.getDeclareType()).isEqualTo(AbstractLimiterBuilder.DeclareType.ATOMIC);
    }

    @Test
    public void build_check() {
        AbstractLimiterTestBuilder testBuilder = new AbstractLimiterTestBuilder();

        // check limiterThrown
        AssertionsEx.assertThrown(() -> testBuilder.build(0))
                .hasMessage(String.format(Verifier.ErrorMsgFormat.REFUSE_NULL, "limiterThrown"))
                .isInstanceOf(IllegalArgumentException.class);

        testBuilder.giveThrown(error -> {
            throw new TestRuntimeException(error);
        });

        UnaryOperator<String> msgTitle = targetTerm -> AbstractLimiterTestBuilder.class.getSimpleName() + "(" + targetTerm + "): ";
        String targetTerm = null;

        // check targetTerm
        AssertionsEx.assertThrown(() -> testBuilder.build(0))
                .hasMessage(msgTitle.apply(targetTerm) + String.format(Verifier.ErrorMsgFormat.REFUSE_NULL_OR_EMPTY, "targetTerm"))
                .isInstanceOf(TestRuntimeException.class);

        targetTerm = "";
        testBuilder.giveTargetTerm(targetTerm);

        AssertionsEx.assertThrown(() -> testBuilder.build(0))
                .hasMessage(msgTitle.apply(targetTerm) + String.format(Verifier.ErrorMsgFormat.REFUSE_NULL_OR_EMPTY, "targetTerm"))
                .isInstanceOf(TestRuntimeException.class);

        targetTerm = "kerker";
        testBuilder.giveTargetTerm(targetTerm);

        // check min and max
        AssertionsEx.assertThrown(() -> testBuilder.build(0))
                .hasMessage(msgTitle.apply(targetTerm) + "min and max can not be null at the same time")
                .isInstanceOf(TestRuntimeException.class);

        // success
        testBuilder.giveMinLimit(1, false)
                .giveMaxLimit(1, false);
        testBuilder.build(1);

        testBuilder.giveMinLimit(null, false)
                .giveMaxLimit(1, false);
        testBuilder.build(Integer.MIN_VALUE);

        testBuilder.giveMinLimit(1, false)
                .giveMaxLimit(null, false);
        testBuilder.build(Integer.MAX_VALUE);
    }

    @Test
    public void buildMaxLimitInclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMaxLimit(1, true);
        }, testBuilder -> {
            testBuilder.buildMaxLimitInclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildMaxLimitExclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMaxLimit(1, false);
        }, testBuilder -> {
            testBuilder.buildMaxLimitExclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildMinLimitInclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, true);
        }, testBuilder -> {
            testBuilder.buildMinLimitInclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildMinLimitExclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, false);
        }, testBuilder -> {
            testBuilder.buildMinLimitExclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildRangeInclusiveInclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, true).giveMaxLimit(3, true);
        }, testBuilder -> {
            testBuilder.buildRangeInclusiveInclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildRangeInclusiveExclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, true).giveMaxLimit(3, false);
        }, testBuilder -> {
            testBuilder.buildRangeInclusiveExclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildRangeExclusiveExclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, false).giveMaxLimit(3, false);
        }, testBuilder -> {
            testBuilder.buildRangeExclusiveExclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildRangeExclusiveInclusiveVerifier() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, false).giveMaxLimit(3, true);
        }, testBuilder -> {
            testBuilder.buildRangeExclusiveInclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildBaseLimiter() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, false).withBase();
        }, testBuilder -> {
            testBuilder.buildBaseLimiter(Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildVolatileLimiter() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, false).withVolatile();
        }, testBuilder -> {
            testBuilder.buildVolatileLimiter(Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildAtomicLimiter() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, false).withAtomic();
        }, testBuilder -> {
            testBuilder.buildAtomicLimiter(Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void wrapMildLimiter() {
        check_methods_have_been_invoke(testBuilder -> {
            testBuilder.giveMinLimit(1, false);
        }, testBuilder -> {
            testBuilder.wrapMildLimiter(Mockito.any());
        }, testBuilder -> {
            testBuilder.buildMildLimiter(0);
        });
    }

    private void check_methods_have_been_invoke(Consumer<AbstractLimiterTestBuilder> init,
                                                Consumer<AbstractLimiterTestBuilder> invokeMethod) {
        check_methods_have_been_invoke(init, invokeMethod, testBuilder -> {
            testBuilder.build(0);
        });
    }

    private void check_methods_have_been_invoke(Consumer<AbstractLimiterTestBuilder> init,
                                                Consumer<AbstractLimiterTestBuilder> invokeMethod,
                                                Consumer<AbstractLimiterTestBuilder> buildAction) {
        AbstractLimiterTestBuilder testBuilder = mockitoTestBuilder();

        init.accept(testBuilder);

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        invokeMethod.accept(Mockito.doAnswer((invocation) -> {
            checkPoint1.set(true);
            return null;
        }).when(testBuilder));

        buildAction.accept(testBuilder);

        AssertionsEx.assertThat(checkPoint1.get()).isTrue();
    }

    private AbstractLimiterTestBuilder mockitoTestBuilder() {
        return Mockito.spy(AbstractLimiterTestBuilder.class)
                .giveTargetTerm("kerker")
                .giveThrown(error -> {
                    throw new TestRuntimeException("");
                });
    }

}
