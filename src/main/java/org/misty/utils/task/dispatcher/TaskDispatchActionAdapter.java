package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.TaskErrorPolicy;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class TaskDispatchActionAdapter<Task> implements TaskDispatchAction<Task> {

    private final Tracked tracked;

    private final Predicate<Task> checker;

    private final ConsumerEx<Task> executor;

    private final BiFunction<Task, Exception, TaskErrorPolicy> errorHandler;

    public TaskDispatchActionAdapter(Tracked tracked,
                                     Predicate<Task> checker,
                                     ConsumerEx<Task> executor,
                                     BiFunction<Task, Exception, TaskErrorPolicy> errorHandler) {
        this.tracked = tracked;
        this.checker = checker;
        this.executor = executor;
        this.errorHandler = errorHandler;
    }

    @Override
    public Tracked getTracked() {
        return this.tracked;
    }

    @Override
    public boolean check(Task task) {
        return this.checker.test(task);
    }

    @Override
    public void execute(Task task) throws Exception {
        this.executor.execute(task);
    }

    @Override
    public TaskErrorPolicy handleError(Task task, Exception e) {
        return this.errorHandler.apply(task, e);
    }

}
