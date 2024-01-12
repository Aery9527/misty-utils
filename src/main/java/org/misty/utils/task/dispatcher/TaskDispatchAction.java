package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskErrorPolicy;

public interface TaskDispatchAction<Task> {

    Tracked getTracked();

    boolean check(Task task);

    void execute(Task task) throws Exception;

    TaskErrorPolicy handleError(Task task, Exception e);

}
