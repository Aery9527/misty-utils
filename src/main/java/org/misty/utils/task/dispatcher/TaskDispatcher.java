package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskWaitResult;

import java.time.Duration;

public interface TaskDispatcher<Task> extends AutoCloseable {

    static <Task> TaskDispatcherBuilder<Task> builder() {
        return TaskDispatcherBuilder.create(Tracked.create());
    }

    static <Task> TaskDispatcherBuilder<Task> builder(String id) {
        return TaskDispatcherBuilder.create(Tracked.create(TaskDispatcher.class, id));
    }

    static <Task> TaskDispatcherBuilder<Task> builder(Tracked tracked) {
        return TaskDispatcherBuilder.create(tracked);
    }

    Tracked getTracked();

    /**
     * @return true if give success, false if give fail
     */
    boolean give(Task task);

    TaskWaitResult waitAllTaskFinish();

    TaskWaitResult waitAllTaskFinish(Duration waitTime);

    @Override
    void close();

}
