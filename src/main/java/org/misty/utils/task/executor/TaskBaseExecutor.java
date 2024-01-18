package org.misty.utils.task.executor;

import org.misty.utils.Tracked;
import org.misty.utils.fi.RunnableEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public abstract class TaskBaseExecutor implements TaskExecutor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 追蹤碼, 方便用來追蹤哪個ExecutorSwitch實例執行錯誤
     */
    private final Tracked tracked;

    /**
     * 預設的錯誤處理器
     */
    private final Function<Exception, TaskErrorPolicy> defaultErrorHandler;

    /**
     * 用來表達目前任務執行狀態的旗標
     */
    private final AtomicReference<TaskExecuteResult> stateFlag = new AtomicReference<>(TaskExecuteResult.EXECUTED);

    public TaskBaseExecutor(TaskBaseExecutorBuilder<?, ?> builder) {
        this.tracked = builder.getTracked();
        this.defaultErrorHandler = Optional.ofNullable(builder.getDefaultErrorHandler()).orElseGet(() -> e -> {
            this.logger.error(this.tracked.say("execute error"), e);
            return TaskErrorPolicy.CONTINUE;
        });
    }

    @Override
    public final TaskExecuteResult run(TaskExecuteAction action) {
        return run(action::execute, action::errorHandler);
    }

    @Override
    public final TaskExecuteResult run(RunnableEx action) {
        return run(action, this.defaultErrorHandler);
    }

    @Override
    public final TaskExecuteResult run(RunnableEx action, Function<Exception, TaskErrorPolicy> errorHandler) {
        if (!TaskExecuteResult.EXECUTED.equals(this.stateFlag.get())) {
            return this.stateFlag.get();
        }

        execute(() -> {
            try {
                action.execute();
            } catch (Exception e) {
                TaskErrorPolicy taskErrorPolicy = null;
                try {
                    taskErrorPolicy = errorHandler.apply(e);
                } catch (Exception ex) {
                    this.logger.error(this.tracked.say("raw error"), e);
                    this.logger.error(this.tracked.say("errorHandler error"), ex);
                }

                if (taskErrorPolicy == null) {
                    this.logger.error(this.tracked.say("force interrupt because TaskErrorPolicy is null"));
                    taskErrorPolicy = TaskErrorPolicy.INTERRUPT;
                }

                if (taskErrorPolicy.isInterrupt()) {
                    this.stateFlag.compareAndSet(TaskExecuteResult.EXECUTED, TaskExecuteResult.INTERRUPTED);
                }
            }
        });

        return TaskExecuteResult.EXECUTED;
    }

    @Override
    public final void close() {
        this.stateFlag.compareAndSet(TaskExecuteResult.EXECUTED, TaskExecuteResult.CLOSED);
        if (!this.stateFlag.get().isClosed()) {
            doClose();
        }
    }

    protected abstract void execute(RunnableEx executeAction);

    protected abstract void doClose();

    @Override
    public Tracked getTracked() {
        return tracked;
    }

    public Function<Exception, TaskErrorPolicy> getDefaultErrorHandler() {
        return defaultErrorHandler;
    }

    public TaskExecuteResult getStateFlag() {
        return stateFlag.get();
    }

}
