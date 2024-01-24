package org.misty.utils.task.dispatcher;

import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.executor.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class TaskOrderedDispatcher<Task> extends TaskBaseDispatcher<Task> {

    private final static int PENDING = 0;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<LinkedList<Task>> actionQueues;

    private final List<AtomicInteger> actionLockCounters;

    public TaskOrderedDispatcher(TaskDispatcherBuilder<Task> builder) {
        super(builder);

        int listSize = builder.getTaskDispatchActionList().size();

        List<LinkedList<Task>> actionQueues = new ArrayList<>(listSize);
        IntStream.range(0, listSize).forEach(index -> actionQueues.add(new LinkedList<>()));
        this.actionQueues = Collections.unmodifiableList(actionQueues);

        List<AtomicInteger> actionLockCounters = new ArrayList<>(listSize);
        IntStream.range(0, listSize).forEach(index -> actionLockCounters.add(new AtomicInteger(PENDING)));
        this.actionLockCounters = Collections.unmodifiableList(actionLockCounters);
    }

    @Override
    protected void handle(TaskExecutor taskExecutor, int index, TaskDispatchAction<Task> action, Task task) {
        LinkedList<Task> queue = this.actionQueues.get(index);
        AtomicInteger actionLockCounter = this.actionLockCounters.get(index);
        processAllTask(taskExecutor, action, task, queue, actionLockCounter);
    }

    protected void processAllTask(TaskExecutor taskExecutor,
                                  TaskDispatchAction<Task> action,
                                  Task task,
                                  LinkedList<Task> queue,
                                  AtomicInteger actionLockCounter) {
        synchronized (queue) {
            queue.add(task);
        }

        Supplier<Task> getTask = () -> {
            synchronized (queue) {
                return queue.poll();
            }
        };

        boolean processing = actionLockCounter.getAndIncrement() != PENDING;
        if (processing) { // only one thread can pass to here at time
            return;
        }

        taskExecutor.run(() -> { // here are another thread execute task
            while (true) {
                Task currentTask = getTask.get();
                if (currentTask == null) {
                    throw new IllegalStateException("IMPOSSIBLE");
                }

                int count;

                try {
                    action.receive(currentTask);
                } catch (Exception e) {
                    action.handleError(action.getTracked(), currentTask, e);
                } finally {
                    count = actionLockCounter.decrementAndGet();
                }

                if (count == PENDING) {
                    break;
                }
            }
        }, e -> {
            this.logger.error(getTracked().toString(), e);
            return TaskErrorPolicy.CONTINUE;
        });
    }

}
