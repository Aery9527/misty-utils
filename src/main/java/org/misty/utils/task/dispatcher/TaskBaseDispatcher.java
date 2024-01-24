package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskWaitResult;
import org.misty.utils.task.executor.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;

public class TaskBaseDispatcher<Task> implements TaskDispatcher<Task> {

    private interface ReceiveProcessor<Task> {
        void process(Task task, TaskDispatchAction<Task> action, int index, ObjIntConsumer<TaskDispatchAction<Task>> actionConsumer);
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Tracked tracked;

    private final TaskExecutor taskExecutor;

    private final BiConsumer<Task, ObjIntConsumer<TaskDispatchAction<Task>>> taskProcessor;

    private volatile boolean closed = false;

    public TaskBaseDispatcher(TaskDispatcherBuilder<Task> builder) {
        List<TaskDispatchAction<Task>> taskDispatchActionList = builder.getTaskDispatchActionList();

        this.tracked = builder.getTracked();
        this.taskExecutor = builder.getTaskExecutorBuilder().get();
        this.taskProcessor = buildTaskProcessor(taskDispatchActionList);
    }

    @Override
    public boolean give(Task task) {
        if (this.closed) {
            return false;
        }

        this.taskProcessor.accept(task, (action, index) -> handle(this.taskExecutor, index, action, task));

        return true;
    }

    @Override
    public Tracked getTracked() {
        return this.tracked;
    }

    @Override
    public TaskWaitResult waitAllTaskFinish() {
        return this.taskExecutor.waitFinish();
    }

    @Override
    public TaskWaitResult waitAllTaskFinish(Duration waitTime) {
        return this.taskExecutor.waitFinish(waitTime);
    }

    @Override
    public void close() {
        this.closed = true;
        this.taskExecutor.close();
    }

    protected void handle(TaskExecutor taskExecutor, int index, TaskDispatchAction<Task> action, Task task) {
        taskExecutor.run(() -> action.receive(task), e -> {
            action.handleError(action.getTracked(), task, e);
            return TaskErrorPolicy.CONTINUE;
        });
    }

    protected BiConsumer<Task, ObjIntConsumer<TaskDispatchAction<Task>>> buildTaskProcessor(List<TaskDispatchAction<Task>> list) {
        ReceiveProcessor<Task> receiveProcessor = (task, action, index, actionConsumer) -> {
            boolean accepted = false;
            try {
                accepted = action.accept(task);
            } catch (Exception e) {
                this.logger.error(this.tracked.say("accept error"), e);
            }

            if (accepted) {
                actionConsumer.accept(action, index);
            }
        };

        if (list.size() == 1) {
            TaskDispatchAction<Task> action0 = list.get(0);
            return (task, actionConsumer) -> receiveProcessor.process(task, action0, 0, actionConsumer);

        } else if (list.size() == 2) {
            TaskDispatchAction<Task> action0 = list.get(0);
            TaskDispatchAction<Task> action1 = list.get(1);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action0, 0, actionConsumer);
                receiveProcessor.process(task, action1, 1, actionConsumer);
            };

        } else if (list.size() == 3) {
            TaskDispatchAction<Task> action0 = list.get(0);
            TaskDispatchAction<Task> action1 = list.get(1);
            TaskDispatchAction<Task> action2 = list.get(2);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action0, 0, actionConsumer);
                receiveProcessor.process(task, action1, 1, actionConsumer);
                receiveProcessor.process(task, action2, 2, actionConsumer);
            };

        } else if (list.size() == 4) {
            TaskDispatchAction<Task> action0 = list.get(0);
            TaskDispatchAction<Task> action1 = list.get(1);
            TaskDispatchAction<Task> action2 = list.get(2);
            TaskDispatchAction<Task> action3 = list.get(3);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action0, 0, actionConsumer);
                receiveProcessor.process(task, action1, 1, actionConsumer);
                receiveProcessor.process(task, action2, 2, actionConsumer);
                receiveProcessor.process(task, action3, 3, actionConsumer);
            };

        } else if (list.size() == 5) {
            TaskDispatchAction<Task> action0 = list.get(0);
            TaskDispatchAction<Task> action1 = list.get(1);
            TaskDispatchAction<Task> action2 = list.get(2);
            TaskDispatchAction<Task> action3 = list.get(3);
            TaskDispatchAction<Task> action4 = list.get(4);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action0, 0, actionConsumer);
                receiveProcessor.process(task, action1, 1, actionConsumer);
                receiveProcessor.process(task, action2, 2, actionConsumer);
                receiveProcessor.process(task, action3, 3, actionConsumer);
                receiveProcessor.process(task, action4, 4, actionConsumer);
            };

        } else {
            return (task, actionConsumer) -> {
                for (int index = 0; index < list.size(); index++) {
                    TaskDispatchAction<Task> action = list.get(index);
                    receiveProcessor.process(task, action, index, actionConsumer);
                }
            };
        }
    }

}
