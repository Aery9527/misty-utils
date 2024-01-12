package org.misty.utils.task.dispatcher;

import org.misty.utils.Tracked;
import org.misty.utils.fi.ConsumerEx;
import org.misty.utils.task.TaskErrorPolicy;
import org.misty.utils.task.executor.TaskExecutor;
import org.misty.utils.task.executor.TaskExecutorBuilder;
import org.misty.utils.verify.Verifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class TaskDispatcherBuilder<Task> {

    private final Predicate<Task> awaysReceiveChecker = task -> true;

    private final List<TaskDispatchAction<Task>> taskDispatchActionList = new ArrayList<>();

    private final Tracked tracked;

    private final TaskExecutorBuilder taskExecutorBuilder;

    private TaskExecutor taskExecutor;

    private BiFunction<Task, Exception, TaskErrorPolicy> defaultErrorHandler;

    public TaskDispatcherBuilder(Tracked tracked) {
        Verifier.refuseNull("tracked", tracked);
        this.tracked = tracked;
        this.taskExecutorBuilder = TaskExecutorBuilder.create(tracked);
    }

    public TaskDispatcherBuilder<Task> giveAction(ConsumerEx<Task> taskReceiver) {
        return giveAction(this.awaysReceiveChecker, taskReceiver);
    }

    public TaskDispatcherBuilder<Task> giveAction(Class<? extends Task> acceptClass, ConsumerEx<Task> taskReceiver) {
        return giveAction(task -> acceptClass.isAssignableFrom(task.getClass()), taskReceiver);
    }

    public TaskDispatcherBuilder<Task> giveAction(Predicate<Task> checker, ConsumerEx<Task> taskReceiver) {
        checkDefaultErrorHandler();
        return giveAction(new TaskDispatchActionAdapter<>(this.tracked, checker, taskReceiver, this.defaultErrorHandler));
    }

    public TaskDispatcherBuilder<Task> giveAction(TaskDispatchAction<Task> taskConsumer) {
        Verifier.refuseNull("taskConsumer", taskConsumer);
        this.taskDispatchActionList.add(taskConsumer);
        return this;
    }


    public TaskDispatcher<Task> build() {
        return null;
    }


    private void checkDefaultErrorHandler() {
    }

    public Tracked getTracked() {
        return tracked;
    }

}
