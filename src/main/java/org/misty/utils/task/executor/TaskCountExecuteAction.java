package org.misty.utils.task.executor;

import org.misty.utils.task.TaskErrorPolicy;

public interface TaskCountExecuteAction {

    void execute(long times) throws Exception;

    TaskErrorPolicy errorHandler(long times, Exception e);

}
