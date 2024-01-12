package org.misty.utils.task.executor;

import org.misty.utils.Tracked;
import org.misty.utils.cycle.Cycle;
import org.misty.utils.cycle.LongCycle;
import org.misty.utils.cycle.LongCycleBuilder;
import org.misty.utils.fi.LongConsumerEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.TaskExecuteResult;
import org.misty.utils.task.TaskWaitResult;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class TaskCountExecutor implements AutoCloseable {

    private final LongCycle cycle;

    private final TaskExecutor executor;

    public TaskCountExecutor(TaskExecutor executor) {
        this.executor = executor;

        UnaryOperator<LongCycleBuilder> cycleBuilder = this.executor.isParallelMode() ? LongCycleBuilder::withAtomic : LongCycleBuilder::withBase;
        Tracked cycleTracked = this.executor.getTracked().link("cycle");
        this.cycle = cycleBuilder.apply(Cycle.longCycleBuilder(cycleTracked))
                .giveRange(0, Long.MAX_VALUE)
                .build();
    }

    public TaskExecuteResult run(TaskCountExecuteAction action) {
        return run(action::execute, action::errorHandler);
    }

    public TaskExecuteResult run(LongConsumerEx action) {
        long count = this.cycle.nextAndGet();
        return this.executor.run(() -> action.execute(count));
    }

    public TaskExecuteResult run(LongConsumerEx action, BiFunction<Long, Exception, TaskErrorPolicy> errorHandler) {
        long count = this.cycle.nextAndGet();
        return this.executor.run(() -> action.execute(count), e -> errorHandler.apply(count, e));
    }

    public TaskWaitResult waitFinish() {
        return this.executor.waitFinish();
    }

    public TaskWaitResult waitFinish(Duration waitTime) {
        return this.executor.waitFinish(waitTime);
    }

    public boolean isSerialMode() {
        return this.executor.isSerialMode();
    }

    public boolean isParallelMode() {
        return this.executor.isParallelMode();
    }

    public Tracked getTracked() {
        return this.executor.getTracked();
    }

    @Override
    public void close() throws Exception {
        this.executor.close();
    }

}
