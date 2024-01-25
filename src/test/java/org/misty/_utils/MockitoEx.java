package org.misty._utils;

import org.assertj.core.util.Lists;
import org.misty.utils.fi.FunctionEx;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public class MockitoEx extends Mockito {

    public static <Target> AtomicBoolean awareInvoke(Target target, Consumer<Target> whenAction) {
        return awareInvoke(target, invocation -> {
        }, whenAction);
    }

    public static <Target> AtomicBoolean awareInvoke(Target target, Consumer<InvocationOnMock> answer, Consumer<Target> whenAction) {
        return awareInvoke(target, invocation -> {
            answer.accept(invocation);
            return null;
        }, t -> {
            whenAction.accept(t);
            return null;
        });
    }

    public static <Target, Return> AtomicBoolean awareInvoke(Target target, Function<InvocationOnMock, Return> answer, FunctionEx<Target, Return> whenAction) {
        AtomicBoolean aware = new AtomicBoolean();
        whenAction.execute(Mockito.doAnswer(invocation -> {
            aware.set(true);
            return answer.apply(invocation);
        }).when(target));
        return aware;
    }

    public static <Target> AtomicInteger awareCount(Target target, Consumer<Target> whenAction) {
        return awareCount(target, invocation -> {
        }, whenAction);
    }

    public static <Target> AtomicInteger awareCount(Target target, Consumer<InvocationOnMock> answer, Consumer<Target> whenAction) {
        return awareCount(target, invocation -> {
            answer.accept(invocation);
            return null;
        }, t -> {
            whenAction.accept(t);
            return null;
        });
    }

    public static <Target, Return> AtomicInteger awareCount(Target target, Function<InvocationOnMock, Return> answer, FunctionEx<Target, Return> whenAction) {
        AtomicInteger aware = new AtomicInteger();
        whenAction.execute(Mockito.doAnswer(invocation -> {
            aware.incrementAndGet();
            return answer.apply(invocation);
        }).when(target));
        return aware;
    }

    public static <Target> List<Object> awareArgs(Target target, Consumer<Target> whenAction) {
        return awareArgs(target, invocation -> {
        }, whenAction);
    }

    public static <Target> List<Object> awareArgs(Target target, Consumer<InvocationOnMock> answer, Consumer<Target> whenAction) {
        return awareArgs(target, invocation -> {
            answer.accept(invocation);
            return null;
        }, t -> {
            whenAction.accept(t);
            return null;
        });
    }

    public static <Target, Return> List<Object> awareArgs(Target target, Function<InvocationOnMock, Return> answer, FunctionEx<Target, Return> whenAction) {
        AtomicReference<List<Object>> reference = new AtomicReference<>();
        whenAction.execute(Mockito.doAnswer(invocation -> {
            reference.set(Lists.newArrayList(invocation.getArguments()));
            return answer.apply(invocation);
        }).when(target));
        return reference.get();
    }

}
