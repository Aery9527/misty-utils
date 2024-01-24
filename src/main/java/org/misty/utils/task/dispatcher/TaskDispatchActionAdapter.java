package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;

import java.util.function.Predicate;

public class TaskDispatchActionAdapter<Task> implements TaskDispatchAction<Task> {

    private final Tracked tracked;

    private final Predicate<Task> accept;

    private final ConsumerEx<Task> receiver;

    private final TaskDispatchErrorHandler<Task> errorHandler;

    public TaskDispatchActionAdapter(Tracked tracked,
                                     Predicate<Task> accept,
                                     ConsumerEx<Task> receiver,
                                     TaskDispatchErrorHandler<Task> errorHandler) {
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
    public void handleError(Tracked actionTracked, Task task, Exception e) {
        this.errorHandler.handleError(actionTracked, task, e);
    }

}
