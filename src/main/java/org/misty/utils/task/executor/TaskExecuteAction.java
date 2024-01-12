package org.misty.utils.task.executor;

import org.misty.utils.task.TaskErrorPolicy;

public interface TaskExecuteAction {

    void execute() throws Exception;

    TaskErrorPolicy errorHandler(Exception e);

}
