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

    public static class AbstractLimiterBuilderQuiz extends
            AbstractLimiterBuilder<
                    Integer,
                    IntLimitVerifierHandler,
                    IntRangeVerifier,
                    IntLimiter,
                    IntMildLimiter,
                    AbstractLimiterBuilderQuiz
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
        AbstractLimiterBuilderQuiz quizBuilder = new AbstractLimiterBuilderQuiz();

        AssertionsEx.assertThat(quizBuilder.getTargetTerm()).isEqualTo(null);

        String targetTerm = "kerker";
        quizBuilder.giveTargetTerm(targetTerm);

        AssertionsEx.assertThat(quizBuilder.getTargetTerm()).isEqualTo(targetTerm);
    }

    @Test
    public void givenMinLimit() {
        AbstractLimiterBuilderQuiz quizBuilder = new AbstractLimiterBuilderQuiz();

        AssertionsEx.assertThat(quizBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(quizBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMaxInclusive()).isTrue();

        quizBuilder.giveMinLimit(1, false);
        AssertionsEx.assertThat(quizBuilder.getMin()).isEqualTo(1);
        AssertionsEx.assertThat(quizBuilder.isMinInclusive()).isFalse();
        AssertionsEx.assertThat(quizBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMaxInclusive()).isTrue();

        quizBuilder.giveMinLimit(2, true);
        AssertionsEx.assertThat(quizBuilder.getMin()).isEqualTo(2);
        AssertionsEx.assertThat(quizBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(quizBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMaxInclusive()).isTrue();
    }

    @Test
    public void givenMaxLimit() {
        AbstractLimiterBuilderQuiz quizBuilder = new AbstractLimiterBuilderQuiz();

        AssertionsEx.assertThat(quizBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(quizBuilder.getMax()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMaxInclusive()).isTrue();

        quizBuilder.giveMaxLimit(1, false);
        AssertionsEx.assertThat(quizBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(quizBuilder.getMax()).isEqualTo(1);
        AssertionsEx.assertThat(quizBuilder.isMaxInclusive()).isFalse();

        quizBuilder.giveMaxLimit(2, true);
        AssertionsEx.assertThat(quizBuilder.getMin()).isEqualTo(null);
        AssertionsEx.assertThat(quizBuilder.isMinInclusive()).isTrue();
        AssertionsEx.assertThat(quizBuilder.getMax()).isEqualTo(2);
        AssertionsEx.assertThat(quizBuilder.isMaxInclusive()).isTrue();
    }

    @Test
    public void givenThrown() {
        AbstractLimiterBuilderQuiz quizBuilder = new AbstractLimiterBuilderQuiz();

        AssertionsEx.assertThat(quizBuilder.getLimiterThrown()).isEqualTo(null);

        LimiterThrown limiterThrown = error -> {
        };
        quizBuilder.giveThrown(limiterThrown);

        AssertionsEx.assertThat(quizBuilder.getLimiterThrown() == limiterThrown).isTrue();
    }

    @Test
    public void declareType() {
        AbstractLimiterBuilderQuiz quizBuilder = new AbstractLimiterBuilderQuiz();

        AssertionsEx.assertThat(quizBuilder.getDeclareType()).isEqualTo(AbstractLimiterBuilder.DeclareType.BASE);

        quizBuilder.withVolatile();
        AssertionsEx.assertThat(quizBuilder.getDeclareType()).isEqualTo(AbstractLimiterBuilder.DeclareType.VOLATILE);

        quizBuilder.withAtomic();
        AssertionsEx.assertThat(quizBuilder.getDeclareType()).isEqualTo(AbstractLimiterBuilder.DeclareType.ATOMIC);
    }

    @Test
    public void build_check() {
        AbstractLimiterBuilderQuiz quizBuilder = new AbstractLimiterBuilderQuiz();

        // check limiterThrown
        AssertionsEx.awareThrown(() -> quizBuilder.build(0))
                .hasMessage(String.format(Verifier.ErrorMsgFormat.REFUSE_NULL, "limiterThrown"))
                .isInstanceOf(IllegalArgumentException.class);

        quizBuilder.giveThrown(error -> {
            throw new TestRuntimeException(error);
        });

        UnaryOperator<String> msgTitle = targetTerm -> AbstractLimiterBuilderQuiz.class.getSimpleName() + "(" + targetTerm + "): ";
        String targetTerm = null;

        // check targetTerm
        AssertionsEx.awareThrown(() -> quizBuilder.build(0))
                .hasMessage(msgTitle.apply(targetTerm) + String.format(Verifier.ErrorMsgFormat.REFUSE_NULL_OR_EMPTY, "targetTerm"))
                .isInstanceOf(TestRuntimeException.class);

        targetTerm = "";
        quizBuilder.giveTargetTerm(targetTerm);

        AssertionsEx.awareThrown(() -> quizBuilder.build(0))
                .hasMessage(msgTitle.apply(targetTerm) + String.format(Verifier.ErrorMsgFormat.REFUSE_NULL_OR_EMPTY, "targetTerm"))
                .isInstanceOf(TestRuntimeException.class);

        targetTerm = "kerker";
        quizBuilder.giveTargetTerm(targetTerm);

        // check min and max
        AssertionsEx.awareThrown(() -> quizBuilder.build(0))
                .hasMessage(msgTitle.apply(targetTerm) + "min and max can not be null at the same time")
                .isInstanceOf(TestRuntimeException.class);

        // success
        quizBuilder.giveMinLimit(1, false)
                .giveMaxLimit(1, false);
        quizBuilder.build(1);

        quizBuilder.giveMinLimit(null, false)
                .giveMaxLimit(1, false);
        quizBuilder.build(Integer.MIN_VALUE);

        quizBuilder.giveMinLimit(1, false)
                .giveMaxLimit(null, false);
        quizBuilder.build(Integer.MAX_VALUE);
    }

    @Test
    public void buildMaxLimitInclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMaxLimit(1, true);
        }, quizBuilder -> {
            quizBuilder.buildMaxLimitInclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildMaxLimitExclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMaxLimit(1, false);
        }, quizBuilder -> {
            quizBuilder.buildMaxLimitExclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildMinLimitInclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, true);
        }, quizBuilder -> {
            quizBuilder.buildMinLimitInclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildMinLimitExclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, false);
        }, quizBuilder -> {
            quizBuilder.buildMinLimitExclusiveVerifier(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        });
    }

    @Test
    public void buildRangeInclusiveInclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, true).giveMaxLimit(3, true);
        }, quizBuilder -> {
            quizBuilder.buildRangeInclusiveInclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildRangeInclusiveExclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, true).giveMaxLimit(3, false);
        }, quizBuilder -> {
            quizBuilder.buildRangeInclusiveExclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildRangeExclusiveExclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, false).giveMaxLimit(3, false);
        }, quizBuilder -> {
            quizBuilder.buildRangeExclusiveExclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildRangeExclusiveInclusiveVerifier() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, false).giveMaxLimit(3, true);
        }, quizBuilder -> {
            quizBuilder.buildRangeExclusiveInclusiveVerifier(Mockito.anyString(), Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildBaseLimiter() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, false).withBase();
        }, quizBuilder -> {
            quizBuilder.buildBaseLimiter(Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildVolatileLimiter() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, false).withVolatile();
        }, quizBuilder -> {
            quizBuilder.buildVolatileLimiter(Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void buildAtomicLimiter() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, false).withAtomic();
        }, quizBuilder -> {
            quizBuilder.buildAtomicLimiter(Mockito.any(), Mockito.any());
        });
    }

    @Test
    public void wrapMildLimiter() {
        check_methods_have_been_invoke(quizBuilder -> {
            quizBuilder.giveMinLimit(1, false);
        }, quizBuilder -> {
            quizBuilder.wrapMildLimiter(Mockito.any());
        }, quizBuilder -> {
            quizBuilder.buildMildLimiter(0);
        });
    }

    private void check_methods_have_been_invoke(Consumer<AbstractLimiterBuilderQuiz> init,
                                                Consumer<AbstractLimiterBuilderQuiz> invokeMethod) {
        check_methods_have_been_invoke(init, invokeMethod, quizBuilder -> {
            quizBuilder.build(0);
        });
    }

    private void check_methods_have_been_invoke(Consumer<AbstractLimiterBuilderQuiz> init,
                                                Consumer<AbstractLimiterBuilderQuiz> invokeMethod,
                                                Consumer<AbstractLimiterBuilderQuiz> buildAction) {
        AbstractLimiterBuilderQuiz quizBuilder = mockitoQuizBuilder();

        init.accept(quizBuilder);

        AtomicBoolean checkPoint1 = new AtomicBoolean(false);
        invokeMethod.accept(Mockito.doAnswer((invocation) -> {
            checkPoint1.set(true);
            return null;
        }).when(quizBuilder));

        buildAction.accept(quizBuilder);

        AssertionsEx.assertThat(checkPoint1.get()).isTrue();
    }

    private AbstractLimiterBuilderQuiz mockitoQuizBuilder() {
        return Mockito.spy(AbstractLimiterBuilderQuiz.class)
                .giveTargetTerm("kerker")
                .giveThrown(error -> {
                    throw new TestRuntimeException("");
                });
    }

}
