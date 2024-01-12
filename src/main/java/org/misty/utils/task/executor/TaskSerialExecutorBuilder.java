package org.misty.utils.task.executor;

public class TaskSerialExecutorBuilder extends TaskBaseExecutorBuilder<TaskSerialExecutor, TaskSerialExecutorBuilder> {

    @Override
    protected TaskSerialExecutor doBuild() {
        return new TaskSerialExecutor(this);
    }

}
