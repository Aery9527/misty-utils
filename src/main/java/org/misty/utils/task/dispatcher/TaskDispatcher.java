package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskGiveResult;
import org.misty.utils.task.TaskWaitResult;

import java.time.Duration;

public interface TaskDispatcher<Task> extends AutoCloseable {

    Tracked getTracked();

    TaskGiveResult give(Task task);

    TaskWaitResult waitFinish();

    TaskWaitResult waitFinish(Duration waitTime);

}
