package org.misty.utils.task.executor;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.verify.Verifier;

import java.util.function.Function;

public abstract class TaskBaseExecutorBuilder<ExecutorType extends TaskExecutor, SelfType extends TaskBaseExecutorBuilder<ExecutorType, SelfType>> {

    /**
     * @see TaskBaseExecutor#tracked
     */
    private Tracked tracked;

    /**
     * @see TaskBaseExecutor#defaultErrorHandler
     */
    private Function<Exception, TaskErrorPolicy> defaultErrorHandler;

    public SelfType giveTracked(Tracked tracked) {
        this.tracked = tracked;
        return (SelfType) this;
    }

    public SelfType giveDefaultErrorHandler(Function<Exception, TaskErrorPolicy> defaultErrorHandler) {
        this.defaultErrorHandler = defaultErrorHandler;
        return (SelfType) this;
    }

    public ExecutorType build() {
        Verifier.refuseNull("tracked", tracked);
        return doBuild();
    }

    protected abstract ExecutorType doBuild();

    public Tracked getTracked() {
        return tracked;
    }

    public Function<Exception, TaskErrorPolicy> getDefaultErrorHandler() {
        return defaultErrorHandler;
    }

}
