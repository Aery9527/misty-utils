package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.task.TaskErrorPolicy;

public interface TaskDispatchAction<Task> {

    Tracked getTracked();

    /**
     * 判斷該task是否要處理, 若回傳true則會由{@link #receive(Task)}接收
     */
    boolean accept(Task task);

    /**
     * 處理該任務
     */
    void receive(Task task) throws Exception;

    /**
     * 當{@link #receive}拋錯時會由這裡處理
     */
    TaskErrorPolicy handleError(Task task, Exception e);

}
