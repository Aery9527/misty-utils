package org.misty.utils.task.dispatcher;

public class TaskSerialDispatcherTest extends TaskBaseDispatcherTest {

    @Override
    public <Task> TaskDispatcherBuilder<Task> builderSetting(TaskDispatcherBuilder<Task> builder) {
        return builder.withSerial();
    }

}
