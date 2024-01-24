package org.misty.utils.task.dispatcher;

public class TaskSerialDispatcher<Task> extends TaskBaseDispatcher<Task> {

    public TaskSerialDispatcher(TaskDispatcherBuilder<Task> builder) {
        super(builder);
    }

}
