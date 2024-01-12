package org.misty.utils.task.executor;

import org.misty.utils.fi.RunnableEx;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskWaitResult;

import java.time.Duration;

public class TaskSerialExecutor extends TaskBaseExecutor {

    public TaskSerialExecutor(TaskSerialExecutorBuilder builder) {
        super(builder);
    }

    @Override
    protected void execute(RunnableEx executeAction) {
        executeAction.execute();
    }

    @Override
    public TaskWaitResult waitFinish() {
        TaskExecuteResult stopFlag = super.getStateFlag();
        if (stopFlag.isExecuted()) {
            return TaskWaitResult.FINISH;
        } else if (stopFlag.isClosed()) {
            return TaskWaitResult.CLOSE;
        } else if (stopFlag.isInterrupt()) {
            return TaskWaitResult.INTERRUPTED;
        } else {
            throw new IllegalStateException("unknown stopFlag: " + stopFlag);
        }
    }

    @Override
    public TaskWaitResult waitFinish(Duration waitTime) {
        return waitFinish();
    }

    @Override
    protected void doClose() {
    }

    @Override
    public boolean isParallelMode() {
        return false;
    }

}
