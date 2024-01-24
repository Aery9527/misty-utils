package org.misty.utils.task.executor;

import org.misty.utils.Tracked;
import org.misty.utils.fi.RunnableEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskWaitResult;

import java.time.Duration;
import java.util.function.Function;

public interface TaskExecutor extends AutoCloseable {

    static TaskExecutorBuilder builder() {
        return TaskExecutorBuilder.create(Tracked.create());
    }

    static TaskExecutorBuilder builder(Tracked tracked) {
        return TaskExecutorBuilder.create(tracked);
    }

    Tracked getTracked();

    default TaskExecuteResult run(TaskExecuteAction action) {
        return run(action::execute, action::errorHandler);
    }

    TaskExecuteResult run(RunnableEx action);

    TaskExecuteResult run(RunnableEx action, Function<Exception, TaskErrorPolicy> errorHandler);

    /**
     * 同{@link #waitFinish()}, 只是無限等待直到任務全部完成返回
     */
    TaskWaitResult waitFinish();

    /**
     * 等待所有送出的任務是否全部執行完成
     *
     * @param waitTime 要等待多久時間
     * @return true:已全部完成; false:仍有未完成的任務正在執行
     */
    TaskWaitResult waitFinish(Duration waitTime);

    default boolean isSerialMode() {
        return !isParallelMode();
    }

    boolean isParallelMode();

    @Override
    void close();

}
