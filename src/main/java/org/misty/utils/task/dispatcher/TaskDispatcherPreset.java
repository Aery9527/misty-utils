package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskGiveResult;
import org.misty.utils.task.TaskWaitResult;
import org.misty.utils.task.executor.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TaskDispatcherPreset<Task> implements TaskDispatcher<Task> {

    private interface ReceiveProcessor<Task> {
        void process(Task task, TaskDispatchAction<Task> action, Consumer<TaskDispatchAction<Task>> actionConsumer);
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Tracked tracked;

    private final TaskExecutor taskExecutor;

    private final int allActionCount;

    private final BiConsumer<Task, Consumer<TaskDispatchAction<Task>>> taskProcessor;

    private final ConsumerEx<TaskGiveResult<Task>> noMatchedAction;

    public TaskDispatcherPreset(TaskDispatcherBuilder<Task> builder) {
        List<TaskDispatchAction<Task>> taskDispatchActionList = builder.getTaskDispatchActionList();

        this.tracked = builder.getTracked();
        this.taskExecutor = builder.getTaskExecutorBuilder().build();
        this.allActionCount = taskDispatchActionList.size();
        this.taskProcessor = buildTaskProcessor(taskDispatchActionList);
        this.noMatchedAction = builder.getNoMatchedAction();
    }

    @Override
    public TaskGiveResult<Task> give(Task task) {
        Map<TaskExecuteResult, AtomicInteger> matchedExecuteResultCount = new HashMap<>();

        this.taskProcessor.accept(task, action -> {
            TaskExecuteResult executeResult = this.taskExecutor.run(() -> action.receive(task), e -> action.handleError(task, e));
            matchedExecuteResultCount.computeIfAbsent(executeResult, key -> new AtomicInteger()).incrementAndGet();
        });

        TaskGiveResult<Task> giveResult = new TaskGiveResult<>(task, this.allActionCount, matchedExecuteResultCount);
        if (giveResult.getMatchedCount() == 0) {
            this.noMatchedAction.execute(giveResult);
        }

        return giveResult;
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
    public void close() throws Exception {
        this.taskExecutor.close();
    }

    protected BiConsumer<Task, Consumer<TaskDispatchAction<Task>>> buildTaskProcessor(List<TaskDispatchAction<Task>> list) {
        ReceiveProcessor<Task> receiveProcessor = (task, action, actionConsumer) -> {
            boolean accepted = false;
            try {
                accepted = action.accept(task);
            } catch (Exception e) {
                this.logger.error(this.tracked.say("accept error"), e);
            }

            if (accepted) {
                actionConsumer.accept(action);
            }
        };

        if (list.size() == 1) {
            TaskDispatchAction<Task> action1 = list.get(0);
            return (task, actionConsumer) -> receiveProcessor.process(task, action1, actionConsumer);

        } else if (list.size() == 2) {
            TaskDispatchAction<Task> action1 = list.get(0);
            TaskDispatchAction<Task> action2 = list.get(1);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action1, actionConsumer);
                receiveProcessor.process(task, action2, actionConsumer);
            };

        } else if (list.size() == 3) {
            TaskDispatchAction<Task> action1 = list.get(0);
            TaskDispatchAction<Task> action2 = list.get(1);
            TaskDispatchAction<Task> action3 = list.get(2);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action1, actionConsumer);
                receiveProcessor.process(task, action2, actionConsumer);
                receiveProcessor.process(task, action3, actionConsumer);
            };

        } else if (list.size() == 4) {
            TaskDispatchAction<Task> action1 = list.get(0);
            TaskDispatchAction<Task> action2 = list.get(1);
            TaskDispatchAction<Task> action3 = list.get(2);
            TaskDispatchAction<Task> action4 = list.get(3);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action1, actionConsumer);
                receiveProcessor.process(task, action2, actionConsumer);
                receiveProcessor.process(task, action3, actionConsumer);
                receiveProcessor.process(task, action4, actionConsumer);
            };

        } else if (list.size() == 5) {
            TaskDispatchAction<Task> action1 = list.get(0);
            TaskDispatchAction<Task> action2 = list.get(1);
            TaskDispatchAction<Task> action3 = list.get(2);
            TaskDispatchAction<Task> action4 = list.get(3);
            TaskDispatchAction<Task> action5 = list.get(4);
            return (task, actionConsumer) -> {
                receiveProcessor.process(task, action1, actionConsumer);
                receiveProcessor.process(task, action2, actionConsumer);
                receiveProcessor.process(task, action3, actionConsumer);
                receiveProcessor.process(task, action4, actionConsumer);
                receiveProcessor.process(task, action5, actionConsumer);
            };

        } else {
            return (task, actionConsumer) -> list.forEach(action -> receiveProcessor.process(task, action, actionConsumer));
        }
    }

}
