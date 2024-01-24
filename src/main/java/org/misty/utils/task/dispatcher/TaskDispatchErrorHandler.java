package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;

public interface TaskDispatchErrorHandler<Task> {

    /**
     * 當{@link TaskDispatchAction#receive}拋錯時會由這裡處理
     */
    void handleError(Tracked actionTracked, Task task, Exception e);

}
