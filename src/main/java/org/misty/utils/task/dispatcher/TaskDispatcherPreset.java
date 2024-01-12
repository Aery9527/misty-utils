package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskGiveResult;
import org.misty.utils.task.TaskWaitResult;
import org.misty.utils.task.executor.TaskExecutor;

import java.time.Duration;
import java.util.function.Function;

public class TaskDispatcherPreset<Task> implements TaskDispatcher<Task> {

    private final Tracked tracked;

    private TaskExecutor taskExecutor;

    private Function<Task, TaskDispatchAction<Task>> taskReceiverSelector;

    public TaskDispatcherPreset(TaskDispatcherBuilder<Task> builder) {
        this.tracked = builder.getTracked();
    }

    @Override
    public TaskGiveResult give(Task task) {
        TaskExecuteResult executeResult;

        TaskDispatchAction<Task> taskReceiver = this.taskReceiverSelector.apply(task);
        if (taskReceiver == null) {

        } else {
            executeResult = this.taskExecutor.run(() -> taskReceiver.execute(task), e -> taskReceiver.handleError(task, e));
        }

        TaskGiveResult giveResult = null;
        return giveResult;
    }

    @Override
    public Tracked getTracked() {
        return this.tracked;
    }

    @Override
    public TaskWaitResult waitFinish() {
        return this.taskExecutor.waitFinish();
    }

    @Override
    public TaskWaitResult waitFinish(Duration waitTime) {
        return this.taskExecutor.waitFinish(waitTime);
    }

    @Override
    public void close() throws Exception {
        this.taskExecutor.close();
    }

}
