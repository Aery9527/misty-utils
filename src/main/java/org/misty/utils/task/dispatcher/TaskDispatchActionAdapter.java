package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.TaskErrorPolicy;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class TaskDispatchActionAdapter<Task> implements TaskDispatchAction<Task> {

    private final Tracked tracked;

    private final Predicate<Task> accept;

    private final ConsumerEx<Task> receiver;

    private final BiFunction<Task, Exception, TaskErrorPolicy> errorHandler;

    public TaskDispatchActionAdapter(Tracked tracked,
                                     Predicate<Task> accept,
                                     ConsumerEx<Task> receiver,
                                     BiFunction<Task, Exception, TaskErrorPolicy> errorHandler) {
        this.tracked = tracked;
        this.accept = accept;
        this.receiver = receiver;
        this.errorHandler = errorHandler;
    }

    @Override
    public Tracked getTracked() {
        return this.tracked;
    }

    @Override
    public boolean accept(Task task) {
        return this.accept.test(task);
    }

    @Override
    public void receive(Task task) throws Exception {
        this.receiver.execute(task);
    }

    @Override
    public TaskErrorPolicy handleError(Task task, Exception e) {
        return this.errorHandler.apply(task, e);
    }

}
