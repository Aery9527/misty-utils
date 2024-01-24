package org.misty.utils.task.dispatcher;

public class TaskDisorderedDispatcherTest extends TaskBaseDispatcherTest {

    @Override
    public <Task> TaskDispatcherBuilder<Task> builderSetting(TaskDispatcherBuilder<Task> builder) {
        return builder.withDisordered();
    }

}
