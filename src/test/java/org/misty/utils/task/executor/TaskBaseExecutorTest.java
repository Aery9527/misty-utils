package org.misty.utils.task.executor;

import org.assertj.core.api.Assertions;

public class TaskBaseExecutorTest {

    public void isSerialMode_isParallelMode(TaskExecutor executor) {
        if (executor instanceof TaskSerialExecutor) {
            Assertions.assertThat(executor.isSerialMode()).isTrue();
            Assertions.assertThat(executor.isParallelMode()).isFalse();

        } else if (executor instanceof TaskParallelExecutor) {
            Assertions.assertThat(executor.isSerialMode()).isFalse();
            Assertions.assertThat(executor.isParallelMode()).isTrue();

        } else {
            throw new IllegalArgumentException("unknown executor: " + executor);
        }
    }

}
